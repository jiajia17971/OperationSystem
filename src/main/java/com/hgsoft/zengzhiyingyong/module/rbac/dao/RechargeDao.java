package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.context.MultipleDataSource;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper.RechargeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ConsumeEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.RechargeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/30.
 * 充值长款数据DAO
 */
@Repository
public class RechargeDao {

    private Logger logger = LoggerFactory.getLogger(RechargeDao.class);
    @Autowired
    private RechargeMapper rechargeMapper;

    public Page<RechargeEntity> query(Page<RechargeEntity> page,String faceCardNum, String startTime, String endTime){
        try {
            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();

            List<RechargeEntity> results = rechargeMapper.query(skip,size,faceCardNum,startTime,endTime);

            long count = rechargeMapper.count(faceCardNum,startTime,endTime);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }


}