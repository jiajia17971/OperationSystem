package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;

import java.math.BigDecimal;


public class AccountEntity extends AbstractUpdatableEntity{


    private String cardno;//通衢卡卡号
    private int acctype;
    private BigDecimal balance;//余额
    private int orgid;

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public int getAcctype() {
        return acctype;
    }

    public void setAcctype(int acctype) {
        this.acctype = acctype;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
