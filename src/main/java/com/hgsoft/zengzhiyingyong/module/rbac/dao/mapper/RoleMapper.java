package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Module;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hegc on 2016/4/3.
 * 角色Mapper接口
 */
@Repository
public interface RoleMapper extends IBaseMapper {

    /**
     * 分页查询
     * @param role
     * @param skip 第几条
     * @param size 每页大小
     */
    List<Role> query(@Param("role") Role role, @Param("skip") int skip, @Param("size") int size);

    /**
     * 获取所有角色集
     * @return
     */
    List<Role> queryAll();

    /**
     * 总数
     * @param role
     * @return
     */
    long count(@Param("role") Role role);

    /**
     * 通过id获取角色
     * @param id
     * @return
     */
    Role get(@Param("id") String id);

    /**
     * 新增角色
     * @param role
     */
    void insert(@Param("role") Role role);

    /**
     * 新增角色模块关系
     * @param id
     * @param modules
     */
    void insertRoleModule(@Param("id") String id, @Param("modules") List<Module> modules);

    /**
     * 更新角色
     * @param role
     */
    void update(@Param("role") Role role);

    /**
     * 通过id删除角色
     * @param id 角色编号
     */
    void delete(@Param("id") String id);

    /**
     * 删除角色用户关系
     * @param id 角色编号
     */
    void deleteRoleUser(@Param("id") String id);

    /**
     * 删除角色模块关系
     * @param id 角色编号
     */
    void deleteRoleModule(@Param("id") String id);

    /**
     * 通过角色名获取角色总数
     * @param role
     * @return
     */
    long countByRoleName(@Param("role") Role role);
}