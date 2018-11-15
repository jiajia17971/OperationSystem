package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.ConsumeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper.TransferMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ConsumeEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.TransferEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/30.
 * 圈存DAO服务类
 */
@Repository
public class TransferDao {

    private Logger logger = LoggerFactory.getLogger(TransferDao.class);
    @Autowired
    private TransferMapper transferMapper;

    public List<TransferEntity> query( String faceCardNum, String startTime, String endTime){
        try {

            List<TransferEntity> results = transferMapper.query(faceCardNum,startTime,endTime);

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }


}