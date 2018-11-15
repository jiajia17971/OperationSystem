package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.BusinessDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ModuleDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.VoucherDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Business;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WQP on 2018/7/30.
 * 业务日志Service
 */
@Service
@Transactional(value = "transactionManager")
public class BusinessService {

    private Logger logger = LoggerFactory.getLogger(BusinessService.class);


    @Autowired
    private BusinessDao businessDao;

    /**
     * 获取开库单
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Business> query(Page<Business> page , Business business,String startTime,String endTime) {
        String start = "";
        String end = "";
        if(StringUtils.isNotBlank(business.getOptTime())){
            String[] datestr = business.getOptTime().split(" - ");
            start = datestr[0];
            end = datestr[1];
        }
        return businessDao.query(page,business,start,end);
    }


    /**
     * 根据id获取开库单
     * @return
     */
    @Transactional(readOnly = true)
    public Business getVoucherById(String id) {

        return businessDao.get(id);
    }
    /**
     * 保存
     * @return
     */
    @Transactional(readOnly = true)
    public int save( Business business) {
        return businessDao.save(business);
    }





}