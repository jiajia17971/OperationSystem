package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;

import java.util.List;

/**
 * Created by hegc on 2016/4/3.
 * 模块领域模型
 */
public class Module extends AbstractUpdatableEntity {

    //模块名称
    private String name;
    //url
    private String url;
    //父模块id
    private String parentId;
    //备注
    private String remark;
    //状态: 1-启用, 0-未启用
    private Integer status;
    //排序
    private int sortNo;
    //等级
    private int levels;
    //图片名称
    private String imageName;
    //子模块
    private List<Module> childModules;

    //此模块可以被哪些角色访问
    private List<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Module> getChildModules() {
        return childModules;
    }

    public void setChildModules(List<Module> childModules) {
        this.childModules = childModules;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
