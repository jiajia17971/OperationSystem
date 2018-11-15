package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.common.mapper.IDataMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ReloadEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 文件重发Mapper接口
 */
@Repository
public interface ReloadMapper extends IBaseMapper {

    /**
     * 分页查询重发项列表
     * @param skip 第几条
     * @param size 每页页码
     * @return
     */
    List<ReloadEntity> query(@Param("skip") int skip, @Param("size") int size, @Param("voucherid") int voucherid);

    /**
     * 统计总数
     * @return
     */
    long count(@Param("voucherid") int voucherid);

    /**
     * save重发项
     * @param reload
     * @return
     */
    int save(@Param("reload") ReloadEntity reload);
    /**
     * save重发项
     * @param reload
     * @return
     */
    int update(@Param("reload") ReloadEntity reload);
    /**
     * delete重发项
     * @param id
     * @return
     */
    boolean delete(@Param("id") String id);

    ReloadEntity get(@Param("id") String id);




}