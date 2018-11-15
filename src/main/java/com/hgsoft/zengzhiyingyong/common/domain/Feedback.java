package com.hgsoft.zengzhiyingyong.common.domain;

import java.io.Serializable;

/**
 * 输出类
 * Created by hegc on 2015/8/18.
 */
public class Feedback implements Serializable {

    private boolean flag;

    private Object data;

    public Feedback() {
        this.flag = true;
    }

    public Feedback(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
