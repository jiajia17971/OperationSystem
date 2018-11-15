package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.ConsumeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ConsumeEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WQP on 2018/8/30.
 * 车辆DAO服务类
 */
@Repository
public class ConsumeDao {

    private Logger logger = LoggerFactory.getLogger(ConsumeDao.class);
    @Autowired
    private ConsumeMapper consumeMapper;

    public Page<ConsumeEntity> query(Page<ConsumeEntity> page,String faceCardNum, String startTime, String endTime){
        try {
            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();
            List<ConsumeEntity> results = consumeMapper.query(skip,size,faceCardNum,startTime,endTime);
            long count = consumeMapper.count(faceCardNum,startTime,endTime);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }
    public Page<ConsumeEntity> query2(Page<ConsumeEntity> page,String faceCardNum,List<Integer> ids, String startTime, String endTime){
        try {

            if(ids.size()<=0){
                ids.add(0);//默认0
            }
            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();
            List<ConsumeEntity> results = consumeMapper.query2(skip,size,faceCardNum,ids,startTime,endTime);
            long count = consumeMapper.count2(ids,faceCardNum,startTime,endTime);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }

    public ConsumeEntity get(String inlistno){
        if(StringUtils.isEmpty(inlistno)){
            return null;
        }
      return  consumeMapper.get(inlistno);

    }

    public int updateComsumeList(Card card){
        return consumeMapper.updateComsumeList(card);
    }

    public boolean updateComsumeLose2(Card card){
        //流水标记

        consumeMapper.updateConsumeHead(card);
        consumeMapper.updateConsumeTail(card);
        consumeMapper.insertLoseDataList(card);

        return true;
    }

    public ConsumeEntity searchStrart(String faceCardNum,String tradeTime){
         return consumeMapper.getByCondition(faceCardNum,tradeTime,"start");
    }
    public ConsumeEntity searchEnd(String faceCardNum,String tradeTime){
         return consumeMapper.getByCondition(faceCardNum,tradeTime,"end");
    }

    public List<ConsumeEntity> searchByTransNo(String faceCardNum,int start,int end,String startTime,String endTime){
        return consumeMapper.getByTransNo(faceCardNum,start,end,startTime,endTime);
    }

}