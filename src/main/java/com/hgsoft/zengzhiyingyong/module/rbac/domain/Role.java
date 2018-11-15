package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;

import java.util.List;

/**
 * Created by hegc on 2016/4/2.
 * 角色领域模型
 */
public class Role extends AbstractUpdatableEntity {

    //角色名
    private String name;

    //是否是默认角色：0-否，1-是
    private Integer status;

    //备注
    private String remark;

    //模块集
    private List<Module> modules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
