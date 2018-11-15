package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import java.math.BigDecimal;

public class AmtFlowEntity {

    private BigDecimal optAmount;
    private BigDecimal postBalance;
    private String businessTime;
    private int idCode;
    private int dataType;//1 圈存 2 消费
    private int transNo;

    public int getTransNo() {
        return transNo;
    }

    public void setTransNo(int transNo) {
        this.transNo = transNo;
    }

    public BigDecimal getOptAmount() {
        return optAmount;
    }

    public void setOptAmount(BigDecimal optAmount) {
        this.optAmount = optAmount;
    }

    public BigDecimal getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(BigDecimal postBalance) {
        this.postBalance = postBalance;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
