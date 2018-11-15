package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalysisEntity {

    private HashMap<String,String> conMap = new HashMap<String, String>();
    private String information;

    public HashMap<String, String> getConMap() {
        return conMap;
    }

    public void setConMap(HashMap<String, String> conMap) {
        this.conMap = conMap;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
