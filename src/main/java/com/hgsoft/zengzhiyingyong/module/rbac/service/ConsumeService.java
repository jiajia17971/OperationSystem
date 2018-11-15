package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.context.MultipleDataSource;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.CardDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ConsumeDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.TransferDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ConsumeEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.TransferEntity;
import com.hgsoft.zengzhiyingyong.util.DataAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by WQP on 2018/8/30.
 * 消费流水Service
 */
@Service
@Transactional(value = "transactionManager")
public class ConsumeService {

    private Logger logger = LoggerFactory.getLogger(ConsumeService.class);


    @Autowired
    private ConsumeDao consumeDao;
    @Autowired
    private TransferDao transferDao;

    /**
     * 查询消费流水
     * @return
     */

    public Page<ConsumeEntity> query(Page<ConsumeEntity> page,String faceCardNum , String startTime, String endTime,String tradeTime,String tradeTime2) {
        if(StringUtils.isEmpty(startTime)||StringUtils.isEmpty(endTime)){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar calc =Calendar.getInstance();
////            endTime = sdf.format(new Date());//当前日期
//            calc.setTime(new Date());
//            calc.add(calc.DATE,-30);
//            Date end  = calc.getTime();
//            endTime = sdf.format(end);
//            calc.add(calc.DATE,-90);
//            Date start = calc.getTime();
//            startTime = sdf.format(start);//九十天之前日期
        }
        List<Integer> ids = new ArrayList<Integer>();

        boolean bool = false;
        if(bool){
            return consumeDao.query(page,faceCardNum,startTime,endTime);
        }else{
            List<ConsumeEntity> consumeList = searchByCondition(faceCardNum,tradeTime,tradeTime2);
            if(consumeList==null||consumeList.size()==0){
                return consumeDao.query(page,faceCardNum,startTime,endTime);
            }else{
                List<TransferEntity> transList = transferDao.query(faceCardNum,tradeTime2,tradeTime);
                ids = DataAnalysis.retrievalData(consumeList,transList);
                return consumeDao.query2(page,faceCardNum,ids,tradeTime,tradeTime2);
            }

        }

    }

    public List<ConsumeEntity> searchByCondition(String faceCardNum,String tradeTime,String tradeTime2){

        if(StringUtils.isEmpty(tradeTime)||StringUtils.isEmpty(tradeTime)){
            return null;
        }else{
            ConsumeEntity start = consumeDao.searchStrart(faceCardNum,tradeTime);
            ConsumeEntity end = consumeDao.searchEnd(faceCardNum,tradeTime2);
            if(start==null||end==null){
                return null;
            }else{
                return consumeDao.searchByTransNo(faceCardNum,start.getSeriNo(),end.getSeriNo(),tradeTime2,tradeTime);
            }
        }

    }

    public boolean remarkConsume(Card card){
        //流水标记
        if(card.getFlowHead()!=null){
            String flowid = card.getFlowHead();

            if(StringUtils.isEmpty(flowid)){
                card.setFlowHead(flowid);
                card.setFlowTail("");
            }else{
                return false;
            }
        }
        return  consumeDao.updateComsumeList(card)>0?true:false;
    }

    public boolean adjustDuplicate2(Card card){
        //流水标记
        if(card.getFlowHead()!=null){
            String flowid = card.getFlowHead();
            String[] arr = flowid.split("_");
            if(arr.length==2){
                card.setFlowHead(arr[0]);
                card.setFlowTail(arr[1]);
            }else{
                return false;
            }
        }
        return  consumeDao.updateComsumeList(card)>0?true:false;
    }
    @Transactional(value = "transactionManager")
    public boolean updateComsumeLose2(Card card ){
        return consumeDao.updateComsumeLose2(card);
    }




}