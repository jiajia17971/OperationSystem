package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper.AccountMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.ConsumeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper.RechargeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.RechargeUpload;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by WQP on 2018/8/30.
 * 账余调整执行DAO
 */
@Repository
public class AccountDao {
    private Logger logger = LoggerFactory.getLogger(AccountDao.class);
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    RechargeMapper rechargeMapper;
    @Autowired
    ConsumeMapper consumeMapper;

    /*
    *充值长款处理
     */
    public boolean adjustAccount(Card card,Voucher voucher){
        if(card==null){
            return false;
        }

        accountMapper.insertRecharge(card,voucher);

        accountMapper.updateAccount(card);
        accountMapper.insertLog(card);

        //流水标记
        rechargeMapper.updateRechargeList(card);

        return true;

    }
    /*
    *充值长款处理
     */
    public boolean insertUploadRecord(RechargeUpload rechargeUpload){


        accountMapper.inserUploadRecord(rechargeUpload);


        return true;

    }

    /**
     * 重复交易
     * @param card
     * @return
     */
    public boolean adjustDuplicate1(Card card,Voucher voucher){
        if(card==null){
            return false;
        }
        accountMapper.insertRecharge(card,voucher);
//        accountMapper.createOrder(card);
        accountMapper.updateAccount(card);
        accountMapper.insertRechargeLog(card);




        return true;

    }
    /**
     * 车道流水丢失
     * @param card
     * @return
     */
    public boolean adjustDataLost1(Card card,Voucher voucher){
        if(card==null){
            return false;
        }
        accountMapper.insertRecharge(card,voucher);
//        accountMapper.createOrder(card);
        accountMapper.updateAccount(card);
        accountMapper.insertRechargeLog(card);
        return true;

    }



}