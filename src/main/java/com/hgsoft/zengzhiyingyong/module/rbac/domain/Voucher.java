package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 开库单实体封装类
 */
public class Voucher extends AbstractUpdatableEntity {

    private int kid;
    private String voucherid;
    private String theme;
    private String description;
    private String orgnization;
    private String owner;
    private String status; //0注销；1待发送 2执行 3待通过 4通过 5驳回
    private int type;//0 车道流水丢失处理;1  车道重复交易;2  银行充值长款;3  记账文件重发;4  跨行30天办理
    private String creTime;
    private String processTime;
    private String endTime;
    private String attachment;
    private int usertype;//当前用户权限
    private Integer[] conditions;

    public Integer[] getConditions() {
        return conditions;
    }

    public void setConditions(Integer[] conditions) {
        this.conditions = conditions;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public void setKid(int id) {
        super.setId(String.valueOf(id));
        System.out.println("set super id : " + id);
    }

    public String getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(String orgnization) {
        this.orgnization = orgnization;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
