package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ModuleDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.UploadDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.VoucherDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.UploadEntity;
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
 * 附件Service
 */
@Service
@Transactional
public class UploadService {

    private Logger logger = LoggerFactory.getLogger(UploadService.class);


    @Autowired
    private UploadDao uploadDao;

    /**
     * 保存附件
     * @return
     */
    @Transactional(readOnly = true)
    public boolean save( UploadEntity uploadEntity) {

        if(uploadEntity!=null){//超级管理员
            return uploadDao.save(uploadEntity);
        }else{
            return false;
        }

    }

    /**
     * 更新附件
     * @return
     */
    @Transactional(readOnly = true)
    public boolean update( int voucherid,String[] ids) {

        if(ids!=null){//
            return uploadDao.update(voucherid,ids);
        }else{
            return false;
        }

    }
    /**
     * 获取开库单附件
     * @return
     */
    @Transactional(readOnly = true)
    public List<UploadEntity> findAttaches(int voucherid) {

        return uploadDao.find(voucherid);
    }
 /**
     * 下载单个附件
     * @return
     */
    @Transactional(readOnly = true)
    public UploadEntity getAttachment(String id) {

        return uploadDao.getAttachment(id);
    }





}