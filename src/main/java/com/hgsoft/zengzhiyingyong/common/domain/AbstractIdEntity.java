package com.hgsoft.zengzhiyingyong.common.domain;

/**
 * 实体基类
 */
public abstract class AbstractIdEntity extends AbstractEntity implements Entity {

    /**
     * 实体编号
     */
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
