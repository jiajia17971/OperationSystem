package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import java.math.BigDecimal;

public class ConsumeEntity {


    private int id;
    private String listno;
    private String faceCardNum;
    private String businessTime;
    private BigDecimal amount;
    private BigDecimal cardBalance;
    private String vehplate;
    private String cardType;
    private String tradeType;
    private String highway;
    private String remark;
    private String flowHead;
    private String flowTail;
    private int seriNo;

    public int getSeriNo() {
        return seriNo;
    }

    public void setSeriNo(int seriNo) {
        this.seriNo = seriNo;
    }

    public String getFlowTail() {
        return flowTail;
    }

    public void setFlowTail(String flowTail) {
        this.flowTail = flowTail;
    }

    public String getFlowHead() {
        return flowHead;
    }

    public void setFlowHead(String flowHead) {
        this.flowHead = flowHead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListno() {
        return listno;
    }

    public void setListno(String listno) {
        this.listno = listno;
    }

    public String getFaceCardNum() {
        return faceCardNum;
    }

    public void setFaceCardNum(String faceCardNum) {
        this.faceCardNum = faceCardNum;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getVehplate() {
        return vehplate;
    }

    public void setVehplate(String vehplate) {
        this.vehplate = vehplate;
    }

    public String getHighway() {
        return highway;
    }

    public void setHighway(String highway) {
        this.highway = highway;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(BigDecimal cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
