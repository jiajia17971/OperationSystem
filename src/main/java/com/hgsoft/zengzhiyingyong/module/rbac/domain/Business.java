package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;

/**
 * 业务实体封装类
 */
public class Business extends AbstractUpdatableEntity {

    private int businessType;//3 记账文件重发 2 调账  11修改开库单 10新建开库单 12 删除开库单
    private String optTime;
    private String operator;
    private String source;
    private String doamin;

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDoamin() {
        return doamin;
    }

    public void setDoamin(String doamin) {
        this.doamin = doamin;
    }
}
