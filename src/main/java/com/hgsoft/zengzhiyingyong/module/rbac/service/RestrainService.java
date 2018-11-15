package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.CardDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ConsumeDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.RetransDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ReloadEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by WQP on 2018/7/30.
 * 重发Service
 */
@Service
@Transactional(value = "fourTransactionManager")
public class RestrainService {

    private Logger logger = LoggerFactory.getLogger(RestrainService.class);

    @Autowired
    private RetransDao retransDao;

    /**
     *
     * @param reload
     * @return
     */
    public boolean restrain(ReloadEntity reload){

        retransDao.delete_sum(reload);

        retransDao.delete_comfirm(reload);

        retransDao.update_Recv(reload);


        return true;
    }





}