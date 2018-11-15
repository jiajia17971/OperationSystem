package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.UploadMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.VoucherMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.UploadEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/7/30.
 * 附件DAO服务类
 */
@Repository
public class UploadDao {

    private Logger logger = LoggerFactory.getLogger(UploadDao.class);

    @Autowired
    private UploadMapper uploadMapper;


    /**
     * 保存附件
     * @param upload
     */
    public boolean save(UploadEntity upload) {
        try {

            return uploadMapper.save(upload);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }
    /**
     * 保存附件
     * @param voucherid
     * @param ids
     */
    public boolean update(int voucherid, String[] ids) {
        try {

            return uploadMapper.update(voucherid,ids);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }
    /**
     * 查询附件
     *
     * @param voucherid
     */
    public List<UploadEntity> find(int voucherid) {
        try {

            return uploadMapper.find(voucherid);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }
    /**
     * 查询附件
     *
     * @param path
     */
    public UploadEntity getAttachment(String path) {
        try {

            return uploadMapper.getAttachment(path);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }

}