package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.BusinessMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Business;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/30.
 * 车辆DAO服务类
 */
@Repository
public class BusinessDao {

    private Logger logger = LoggerFactory.getLogger(BusinessDao.class);
    @Autowired
    private BusinessMapper businessMapper;

    public Page<Business> query(Page<Business> page, Business business, String start, String end){
        try {
            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();
            List<Business> results = businessMapper.query(skip, size,business,start,end);
            long count = businessMapper.count(business,start,end);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }
    public int save(Business business){
        return businessMapper.save(business);
//        return 1;
    }
    public Business get (String id){
        return businessMapper.get(id);
    }

}