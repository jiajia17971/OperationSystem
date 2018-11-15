package com.hgsoft.zengzhiyingyong.util;

/**
 * Created by hegc on 2016/7/20.
 * 上传状态枚举类
 */
public enum UploadStatus {
    SUCCESS(1, "上传文件成功！"),
    SUFFIX_ERROR(2, "文件类型不正确，请核对后再导入！"),
    SIZE_ERROR(3, "文件大小超过限制，数据行不能超过行！"),
    TEMPLATE_ERROR(4, "文件模版不正确，请核对后再导入！"),
    EMPTY_ERROR(5, "上传的文件没有数据存在！"),
    EXCEPTION_ERROR(6, "上传文件过程出错！"),
    FILE_ERROR(7, "上传文件中有错误的数据！");

    private int id;

    private String desc;

    UploadStatus(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int id() {
        return this.id;
    }

    public String desc() {
        return this.desc;
    }

}
