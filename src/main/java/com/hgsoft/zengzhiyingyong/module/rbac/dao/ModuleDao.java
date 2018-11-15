package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.ModuleMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hegc on 2016/4/4.
 */
@Repository
public class ModuleDao {

    private Logger logger = LoggerFactory.getLogger(ModuleDao.class);

    @Autowired
    private ModuleMapper mapper;

    /**
     * 获取树的数据(生效)
     * @return
     */
    public List<Module> getTreeData() {
        try {
            return mapper.getTreeData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 获取所有树的数据
     * @return
     */
    public List<Module> getAllTreeData() {
        try {
            return mapper.getAllTreeData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 获取模块
     * @param id
     * @return
     */
    public Module get(String id) {
        try {
            return mapper.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 根据id删除模块
     * @param id
     */
    public void delete(String id) {
        try {
            mapper.delete(id);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 删除模块角色关系
     * @param id
     */
    public void deleteModuleRole(String id){
        try {
            mapper.deleteModuleRole(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 保存模块
     * @param module
     */
    public void save(Module module) {
        try {
            mapper.save(module);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 更新模块
     * @param module
     */
    public void update(Module module){
        try {
            mapper.update(module);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 统计模块名总数
     * @param module
     * @return
     */
    public int countModuleName(Module module){
        try {
            return mapper.countModuleName(module);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 根据用户名获取菜单
     * @param id
     * @return
     */
    public List<Module> getMenus(String id){
        try {
            return mapper.getMenus(id);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 获取url可以被哪些角色访问
     * @return
     */
    public List<Module> getUrlRoles() {
        try {
            return mapper.getUrlRoles();
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }
}