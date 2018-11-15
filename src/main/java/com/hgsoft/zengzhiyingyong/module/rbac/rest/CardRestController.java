package com.hgsoft.zengzhiyingyong.module.rbac.rest;

import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import com.hgsoft.zengzhiyingyong.module.rbac.service.BusinessService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.CardService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UploadService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WQP on 2018/7/31.
 * Business restful控制器
 */
@RestController
@RequestMapping("/api/v1/card")
public class CardRestController extends BasePageController<Card>{

    private Logger logger = LoggerFactory.getLogger(CardRestController.class);

    @Autowired
    private CardService cardService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private UploadService uploadService;

    /**
     * 分页查询开库单卡项
     * @param page
     * @param limit
     * @param voucherid
     * @param faceCardNum
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/query",method =RequestMethod.GET )
    public Map<String, Object>  query(String page, String limit, String voucherid, String faceCardNum) throws IOException {

        Page<Card> pages = super.getPage(page, limit);
        Page<Card> pg = cardService.query( pages,voucherid,faceCardNum);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", pg.getTotalCount());
        result.put("data", pg.getResult());
        return result;
    }

    /**
     * 保存卡信息
     * @param card
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/save",method =RequestMethod.POST )
    public Card  save(Card card) throws IOException {

        try {
            logger.info("反序列化的card为 :" + card);
            card.setStatus(0);
            Card c = cardService.save(card);
            return c;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return  null;
        }
    }

    /**
     * 注销卡片
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/delete/{id}",method =RequestMethod.GET )
    public boolean  delete(@PathVariable String id) throws IOException {

        try {
            return  cardService.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 开库单执行
     * @param voucherid
     * @return
     */
    @RequestMapping(value = "/toAdjustAccount/{voucherid}",method =RequestMethod.GET )
    public ResponseEntity toAdjustAccount(@PathVariable("voucherid") String voucherid){
        if(voucherid==null){
            return null;
        }
        return cardService.adjustAccount(voucherid);

    }
    @RequestMapping(value = "/sendcheck/{voucherid}",method =RequestMethod.GET )
    public ResponseEntity sendcheck(@PathVariable("voucherid") String voucherid){
        if(voucherid==null){
            return null;
        }
        ResponseEntity responseEntity = new ResponseEntity();

        Voucher exvoucher = voucherService.getVoucherById(voucherid);
        if(exvoucher==null){
            responseEntity.setResponse(true);
        }else{
           List<UploadEntity> clist = uploadService.findAttaches(Integer.parseInt(exvoucher.getId()));
           if(clist!=null&&clist.size()>0){
               Voucher v = new Voucher();
               v.setId(voucherid);
               v.setStatus("3");
               int i = voucherService.sendcheck(v);
               if(i>0){
                   responseEntity.setResponse(true);
               }else{
                   responseEntity.setResponse(false);

               }
           }else {
               responseEntity.setResponse(false);
               responseEntity.setResponseNote("该开库单尚未上传附件请上传附件！");
           }
        }

        return responseEntity;

    }






}