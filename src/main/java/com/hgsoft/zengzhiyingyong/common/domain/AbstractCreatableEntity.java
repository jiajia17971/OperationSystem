package com.hgsoft.zengzhiyingyong.common.domain;

import java.util.Date;

/**
 * 可新增的实体基类
 */
public abstract class AbstractCreatableEntity extends AbstractIdEntity implements Entity {

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
