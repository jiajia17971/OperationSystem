package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import java.math.BigDecimal;

public class RechargeUpload {

    private int id;
    private String faceCardNum;
    private BigDecimal optAmount;
    private String centerId;
    private String listNo;
    private String json;
    private String fileName;
    private String fileCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaceCardNum() {
        return faceCardNum;
    }

    public void setFaceCardNum(String faceCardNum) {
        this.faceCardNum = faceCardNum;
    }

    public BigDecimal getOptAmount() {
        return optAmount;
    }

    public void setOptAmount(BigDecimal optAmount) {
        this.optAmount = optAmount;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getListNo() {
        return listNo;
    }

    public void setListNo(String listNo) {
        this.listNo = listNo;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
