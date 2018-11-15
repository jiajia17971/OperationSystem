package com.hgsoft.zengzhiyingyong.support;

/**
 * Created by hegc on 2016/4/8.
 * 用户状态枚举类
 */
public enum UserStatus {

    LOCK(1, "锁定"),

    NOT_LOCK(0, "未锁定");

    private int id;

    private String desc;

    UserStatus(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int id(){
        return this.id;
    }

    public String desc() {
        return desc;
    }
}
