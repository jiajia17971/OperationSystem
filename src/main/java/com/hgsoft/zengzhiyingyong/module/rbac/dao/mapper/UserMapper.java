package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hegc on 2016/4/2.
 * 用户Mapper接口
 */
@Repository
public interface UserMapper extends IBaseMapper {

    /**
     * 分页查询用户信息
     * @param user 用户
     * @param skip 第几条
     * @param size 每页页码
     * @return
     */
    List<User> query(@Param("user") User user, @Param("skip") int skip, @Param("size") int size);

    /**
     * 统计总数
     * @param user
     * @return
     */
    long count(@Param("user") User user);

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    User get(@Param("id") String id);

    /**
     * 通过登录名获取用户信息
     * @param loginName
     * @return
     */
    User getByLoginName(@Param("loginName") String loginName);

    /**
     * 更新用户
     * @param user
     */
    void update(@Param("user") User user);

    /**
     * 新增用户
     * @param user
     */
    void insert(@Param("user") User user);

    /**
     * 新增用户角色关系
     * @param id
     * @param roles
     */
    void insertUserRole(@Param("id") String id, @Param("roles") List<Role> roles);

    /**
     * 通过id删除用户
     * @param id
     */
    void delete(@Param("id") String id);

    /**
     * 批量删除用户
     * @param ids
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * 删除用户时, 删除用户角色关系
     * @param id
     */
    void deleteUserRole(@Param("id") String id);

    /**
     * 更新登录时间
     * @param loginName
     */
    void updateLoginTime(@Param("loginName") String loginName);

    /**
     * 启用、禁用用户
     * @param id
     * @param status
     */
    void enable(@Param("id") String id, @Param("status") int status);

    long countByLoginName(@Param("user") User user);

}