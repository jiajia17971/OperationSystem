package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hegc on 2016/4/4.
 * 模块Mapper接口类
 */
@Repository
public interface ModuleMapper extends IBaseMapper {

    /**
     * 获取树节点数据
     * @return
     */
    List<Module> getTreeData();

    /**
     * 获取所有树节点数据
     * @return
     */
    List<Module> getAllTreeData();

    /**
     * 获取模块信息
     * @param id
     * @return
     */
    Module get(@Param("id") String id);

    /**
     * 删除模块
     */
    void delete(@Param("id") String id);

    /**
     * 删除模块角色关系
     * @param id
     */
    void deleteModuleRole(@Param("id") String id);

    /**
     * 保存模块
     * @param module
     */
    void save(@Param("module") Module module);

    /**
     * 更新模块
     * @param module
     */
    void update(@Param("module") Module module);

    /**
     * 获取模块名字是否重复
     * @param module
     * @return
     */
    int countModuleName(@Param("module") Module module);

    /**
     * 获取个人菜单数据
     * @param id 用户id
     * @return
     */
    List<Module> getMenus(@Param("id") String id);

    /**
     * 获取url可以被哪些角色访问--供shiro调用
     * @return
     */
    List<Module> getUrlRoles();
}
