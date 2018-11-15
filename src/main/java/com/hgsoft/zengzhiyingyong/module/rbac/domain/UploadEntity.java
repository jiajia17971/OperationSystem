package com.hgsoft.zengzhiyingyong.module.rbac.domain;

import com.hgsoft.zengzhiyingyong.common.domain.AbstractUpdatableEntity;
/**
 * 文件上传实体封装类
 */
public class UploadEntity extends AbstractUpdatableEntity {

    private String id;
    private String filepath;
    private String extension;
    private int status;
    private long size;
    private int voucherid;
    private String orname;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(int voucherid) {
        this.voucherid = voucherid;
    }

    public String getOrname() {
        return orname;
    }

    public void setOrname(String orname) {
        this.orname = orname;
    }
}
