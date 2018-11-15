package com.hgsoft.zengzhiyingyong.common.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页控制器基类，用于生成Page实例
 * Created with IntelliJ IDEA.
 * User: hegc
 * Date: 2016-6-13
 * Time: 下午1:09
 * To change this template use File | Settings | File Templates.
 */
public class BasePageController<T> {

    /**
     * 获得Page实例
     *
     * @param pageNo   第几页
     * @param pageSize 每页大小
     * @return
     */
    public Page<T> getPage(String pageNo, String pageSize) {
        Page<T> page = new Page<T>();
        try {
            if (StringUtils.isNotBlank(pageNo)) {
                page.setPageNo(Integer.parseInt(pageNo));
            } else {
                page.setPageNo(1);
            }
            if (StringUtils.isNotBlank(pageSize)) {
                page.setPageSize(Integer.parseInt(pageSize));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            page.setPageNo(1);
            page.setPageSize(10);
        }
        return page;
    }
}
