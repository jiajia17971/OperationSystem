package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.*;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import com.hgsoft.zengzhiyingyong.util.DateUtil;
import com.hgsoft.zengzhiyingyong.util.InitData;
import com.hgsoft.zengzhiyingyong.util.MD5Coder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by WQP on 2018/7/30.
 * 操作卡项Service
 */
@Service
public class CardService {

    private Logger logger = LoggerFactory.getLogger(CardService.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    @Autowired
    private CardDao cardDao;

    @Autowired
    private BusinessDao businessDao;

    
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ConsumeDao consumeDao;

    @Autowired
    private VoucherDao voucherDao;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private ConsumeService consumeService;


    /**
     * 查询卡列表
     * @return
     */
    @Transactional(value="transactionManager")
    public Page<Card> query(Page<Card> page , String voucherid,String faceCardNum) {

        return cardDao.query(page,voucherid,faceCardNum);
    }

    /**
     * 保存卡项
     * @return
     */
    @Transactional(value="transactionManager")
    public Card save(Card card) {
        if(!StringUtils.isEmpty(card.getId())){
            cardDao.update(card);
        }else{
            cardDao.save(card);
        }
        return card;
    }
    /**
     * 删除卡项
     * @return
     */
    @Transactional(value="transactionManager")
    public boolean delete(String id) {

        return cardDao.delete(id);
    }
    @Transactional(value="transactionManager")
    public Card get(String id){
        return cardDao.get(id);
    }

    @Transactional(value="transactionManager")
    public Card getByCardNo(String faceCardNum){
        return cardDao.getByCardNo(faceCardNum);
    }


    public ResponseEntity adjustAccount(String voucherid){
         int failedNum = 0;
         int successNum = 0;
         int totalNum = 0;
         List<String> list = new ArrayList<String>();
        try{
        Voucher exvoucher =  voucherDao.getVoucherById(voucherid);
        if(exvoucher==null){
            logger.error("开库单："+ voucherid+"未查询到数据!");
            return null;
        }
        if(exvoucher.getStatus()=="0"||exvoucher.getStatus()=="2"){
            logger.error("开库单："+ exvoucher.getVoucherid()+"已注销或已经执行!");
            return null;
        }

        ResponseEntity res = new ResponseEntity();
        List<Card> cardList =  cardDao.getCardByVoucher(voucherid);
        res.setTotalNum(cardList!=null?cardList.size():0);
        if(cardList==null||cardList.size()<=0){
            return null;
        }else{
            for(int i=0;i<cardList.size();i++) {
                Card card = cardList.get(i);
                if(card.getStatus()==2){
                    continue;
                }
                //查询传入参数
                AccountEntity accInfo =  searchForRecharge(card);
                if(accInfo==null){
                    logger.error("没有查询到card:"+card.getFaceCardNum()+"的虚拟账户");
                    return null;
                }
                card.setAccType(accInfo.getAcctype());
                card.setBalance(accInfo.getBalance());
                card.setOptAmount(card.getOptAmount().setScale(0,BigDecimal.ROUND_DOWN));
                boolean b = false;
                Random r = new Random();
                String listno = "00020150822"+ String.valueOf(r.nextInt(1000000000));
                if(exvoucher.getType()==2){//充值长款
                    card.setListNo(listno);
                    b = rechargeService.adjustAccount(card,exvoucher);
                    RechargeUpload rechargeUpload = combineUploadInfo(card,accInfo);
                    boolean c = rechargeService.insertUploadRecord(rechargeUpload);

                    b = b&&c;
                }else if(exvoucher.getType()==1){//车道重复交易
                    card.setListNo(listno);
//                    card.setFlowTail(listno);
                    b = rechargeService.adjustDuplicate1(card,exvoucher);
                    boolean c =  consumeService.adjustDuplicate2(card);
                    b = b&&c;
                }else if(exvoucher.getType()==0){//车道流水丢失
                    card.setListNo(listno);
//                    card.setOptAmount(card.getOptAmount().multiply(new BigDecimal(100)));
                    if(card.getFlowHead()!=null){
                        String flowid = card.getFlowHead();
                        String[] arr = flowid.split("_");
                        ConsumeEntity head =  consumeDao.get(arr[0]);
                        ConsumeEntity tail =  consumeDao.get(arr[1]);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:sss");
                        long start = sdf.parse(head.getBusinessTime()).getTime();
                        long end = sdf.parse(tail.getBusinessTime()).getTime();
                        card.setTradeTime(sdf.format(new Date(start+(long)Math.ceil((end-start)/2))));
                        card.setFlowHead(head.getListno());
                        card.setFlowTail(tail.getListno());
                    }

                    b = rechargeService.adjustDataLost1(card,exvoucher);
                    String idcode = exvoucher.getVoucherid();
                    if(StringUtils.isNotEmpty(idcode)&&idcode.length()>20){
                        idcode = idcode.substring(13).replaceAll("-","");
                    }else{
                        idcode = "-1";
                    }
                    card.setListNo(idcode);
                    boolean c = consumeService.updateComsumeLose2(card);
                    b = b&&c;
                }else if(exvoucher.getType()==5){
                    card.setListNo(listno);
                    b = rechargeService.adjustDuplicate1(card,exvoucher);
                    boolean c = consumeService.remarkConsume(card);
                }
                if (b) {
                    card.setStatus(1);
                    save(card);
                    successNum++;
                } else {
                    failedNum++;
                    list.add(card.getFaceCardNum());
                }

            }


            if(successNum>0){
                res.setResponse(true);
            }
            res.setFailedNum(failedNum);
        }

            //记录操作日志(执行重发)
            Business business = new Business();
            business.setOperator(SecurityContextHolder.getUser().getId());
            business.setOptTime(sdf.format(new Date()));
            business.setBusinessType(exvoucher.getType());
            business.setSource(voucherid);
            businessDao.save(business);

            //更改开库单状态2：办结
            Voucher v = new Voucher();
            v.setId(voucherid);
            v.setStatus("2");
            v.setProcessTime(sdf.format(new Date()));
            voucherDao.update(v);//存在id，更新开库单

            return res;

        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }


    }

    public List<Card> queryByVoucher(String id){

        return cardDao.queryByVoucher(id);
    }


    public RechargeUpload combineUploadInfo(Card card ,AccountEntity accInfo){
        RechargeUpload rechargeUpload = new RechargeUpload();
        try{

            rechargeUpload.setFaceCardNum(card.getFaceCardNum());
            rechargeUpload.setListNo(card.getListNo());
            int orgid = accInfo.getOrgid();
            String centerid = "";
            String format = DateUtil.DATE_DEFUAL_FORMAT4;
            switch (orgid){
                case 102 ://工行
                    centerid ="1" + InitData.pointCode_icbc;
                    break;
                case 103 ://农行
                    centerid ="1" + InitData.pointCode_abc;
                    break;
                case 104 ://建行
                    centerid ="1" + InitData.pointCode_ccb;
                    break;
                case 105 ://中行
                    centerid ="1" + InitData.pointCode_boc;
                    break;
                case 402 ://农商行
                    centerid ="1" + InitData.pointCode_rcb;
                    break;
                case 100 ://邮储
                    centerid ="1" + InitData.pointCode_psbc;
                    break;
                default:
                    logger.error("未查询到虚拟账户机构号!");
                    return null;

            }
            centerid = centerid  + new SimpleDateFormat(format).format(new Date())+card.getFaceCardNum().substring(14);
            rechargeUpload.setCenterId(centerid);
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("cardId","4202"+card.getFaceCardNum());
            map.put("id",centerid);
            map.put("rechargeAmount",card.getOptAmount().multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN));
            map.put("paidAmount",card.getOptAmount().multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN));
            map.put("giftAmount",0);
            ObjectMapper objectMapper = new ObjectMapper();
            rechargeUpload.setJson(objectMapper.writeValueAsString(map));
            rechargeUpload.setFileName("TRANSACTION_RECHARGEUPLOAD_REQ_42_" + new SimpleDateFormat(DateUtil.DATE_DEFUAL_FORMAT5).format(new Date())+card.getFaceCardNum().substring(14) +".json");
            rechargeUpload.setFileCode(MD5Coder.encodeMD5Hex(objectMapper.writeValueAsString(map)).toUpperCase());
            rechargeUpload.setOptAmount(card.getOptAmount().multiply(new BigDecimal(100)));
        }catch (Exception e){
                logger.error("充值上传记录构造错误！");
        }

        return rechargeUpload;
    }

    @Transactional(value="thirdTransactionManager")
    public AccountEntity searchForRecharge(Card card){
        if(card==null){
           return null;
        }
        return cardDao.searchForRecharge(card);
    }

    public List<Card> getVoucherByCardNo(String faceCardNum){

        List<Card> list = cardDao.getVoucherByCardNo(faceCardNum);
        return list;
    }

}