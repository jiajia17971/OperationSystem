package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Business;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 用户Mapper接口
 */
@Repository
public interface BusinessMapper extends IBaseMapper {

    /**
     * 分页查询日志信息
     * @param skip 第几条
     * @param size 每页页码
     * @return
     */
    List<Business> query(@Param("skip") int skip, @Param("size") int size, @Param("business") Business business, @Param("start") String start, @Param("end") String end);

    /**
     * 统计总数
     * @return
     */
    long count( @Param("business") Business business, @Param("start") String start, @Param("end") String end);


    int save(@Param("business") Business business);
    /**
     * save 操作日志
     * @param business
     * @return
     */
    int update(@Param("business") Business business);

    /**
     * 通过id获取操作日志信息
     * @param id
     * @return
     */
    Business get(@Param("id") String  id);



}