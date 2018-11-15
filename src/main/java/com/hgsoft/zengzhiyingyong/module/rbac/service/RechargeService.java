package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.context.MultipleDataSource;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.AccountDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ConsumeDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.RechargeDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by WQP on 2018/8/30.
 * 充值长款流水Service
 */
@Service
@Transactional(value = "thirdTransactionManager")
public class RechargeService {

    private Logger logger = LoggerFactory.getLogger(RechargeService.class);


    @Autowired
    private RechargeDao rechargeDao;
    @Autowired
    private AccountDao accountDao;

    /**
     * 查询充值流水
     * @return
     */
    public Page<RechargeEntity> query(Page<RechargeEntity> page, String faceCardNum , String startTime, String endTime) {
        if(StringUtils.isEmpty(startTime)||StringUtils.isEmpty(endTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calc =Calendar.getInstance();
            endTime = sdf.format(new Date());//当前日期
            calc.setTime(new Date());
            calc.add(calc.DATE,-90);
            Date start = calc.getTime();
            startTime = sdf.format(start);//九十天之前日期
        }

        return rechargeDao.query(page,faceCardNum,startTime,endTime);
    }

    public boolean adjustAccount(Card card, Voucher voucher){

        return  accountDao.adjustAccount(card,voucher);

    }
    public boolean insertUploadRecord(RechargeUpload rechargeUpload){


        return  accountDao.insertUploadRecord(rechargeUpload);

    }
    public boolean adjustDuplicate1(Card card,Voucher voucher){

        return  accountDao.adjustDuplicate1(card,voucher);

    }
    public boolean adjustDataLost1(Card card,Voucher voucher){

        return  accountDao.adjustDataLost1(card,voucher);

    }





}