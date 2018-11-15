package com.hgsoft.zengzhiyingyong.common.domain;

/**
 * Created by hegc on 2015/8/13.
 */
public abstract class AbstractEntity implements Entity {
    public AbstractEntity() {
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            AbstractEntity entity = (AbstractEntity)o;
            if(this.getId() != null) {
                if(!this.getId().equals(entity.getId())) {
                    return false;
                }
            } else if(entity.getId() != null) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getId() != null?this.getId().hashCode():0;
    }
}