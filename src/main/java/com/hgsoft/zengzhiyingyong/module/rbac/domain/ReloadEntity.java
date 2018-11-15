package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;
import com.hgsoft.zengzhiyingyong.common.domain.Entity;

import java.math.BigDecimal;

/**
 * 文件重传实体封装类
 */
public class ReloadEntity implements Entity {

    private String id;
    private int voucherid;//开库单id
    private String orgid;//机构id
    private String batchNo;//批次号
    private int EExtiNum;//数量
    private BigDecimal EExtiMoney;//金额
    private String remark;//备注信息
    private int status;//状态
    private String createTime;//关联消费流水号码
    private int vstate;

    public int getVstate() {
        return vstate;
    }

    public void setVstate(int vstate) {
        this.vstate = vstate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(int voucherid) {
        this.voucherid = voucherid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getEExtiNum() {
        return EExtiNum;
    }

    public void setEExtiNum(int EExtiNum) {
        this.EExtiNum = EExtiNum;
    }

    public BigDecimal getEExtiMoney() {
        return EExtiMoney;
    }

    public void setEExtiMoney(BigDecimal EExtiMoney) {
        this.EExtiMoney = EExtiMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
