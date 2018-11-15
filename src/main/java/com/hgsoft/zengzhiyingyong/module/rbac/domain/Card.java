package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;

import java.math.BigDecimal;

/**
 * 开库单通衢卡实体封装类
 */
public class Card extends AbstractUpdatableEntity {

    private String id;
    private int accType;
    private String orgid;
    private String operator;
    private BigDecimal balance;
    private String faceCardNum;//通衢卡卡号
    private String vehiclePlate;//车牌号码
    private String vehiclePlateColor;//车牌颜色
    private BigDecimal optAmount;//操作金额
    private int status;//状态
    private String remark;//备注信息
    private String flowHead;//关联消费流水号码
    private String flowTail;//关联消费流水号码
    private String voucherid;//开库单id(区别于开库单编号)
    private String tradeTime;
    private int vstate;
    private String listNo;

    public String getListNo() {
        return listNo;
    }

    public void setListNo(String listNo) {
        this.listNo = listNo;
    }

    public int getVstate() {
        return vstate;
    }

    public void setVstate(int vstate) {
        this.vstate = vstate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getAccType() {
        return accType;
    }

    public void setAccType(int accType) {
        this.accType = accType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFaceCardNum() {
        return faceCardNum;
    }

    public void setFaceCardNum(String faceCardNum) {
        this.faceCardNum = faceCardNum;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehiclePlateColor() {
        return vehiclePlateColor;
    }

    public void setVehiclePlateColor(String vehiclePlateColor) {
        this.vehiclePlateColor = vehiclePlateColor;
    }

    public BigDecimal getOptAmount() {
        return optAmount;
    }

    public void setOptAmount(BigDecimal optAmount) {
        this.optAmount = optAmount;
    }

    public String getFlowHead() {
        return flowHead;
    }

    public void setFlowHead(String flowHead) {
        this.flowHead = flowHead;
    }

    public String getFlowTail() {
        return flowTail;
    }

    public void setFlowTail(String flowTail) {
        this.flowTail = flowTail;
    }

    public String getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
}
