package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import java.util.List;

public class ResponseEntity {

    private boolean response;
    private int successNum;
    private int failedNum;
    private int totalNum;
    private List<String> failedList;
    private String responseNote;



    public String getResponseNote() {
        return responseNote;
    }

    public void setResponseNote(String responseNote) {
        this.responseNote = responseNote;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public int getSuccessNum() {
        return this.totalNum-this.failedNum;
    }

    public int getFailedNum() {
        return failedNum;
    }

    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<String> getFailedList() {
        return failedList;
    }

    public void setFailedList(List<String> failedList) {
        this.failedList = failedList;
    }
}
