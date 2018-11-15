package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.RoleMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Module;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hegc on 2016/4/3.
 * 角色DAO
 */
@Repository
public class RoleDao {
    private Logger logger = LoggerFactory.getLogger(RoleDao.class);

    @Autowired
    private RoleMapper mapper;

    /**
     * 分页获取角色集
     * @param role
     * @param page
     * @return
     */
    public Page<Role> query(Role role, Page page) {
        try {
            int size = page.getPageSize();
            int skip = (page.getPageNo() - 1) * size;
            List<Role> results = mapper.query(role, skip, size);
            long count = mapper.count(role);
            page.setResult(results);
            page.setTotalCount(count);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 获取所有角色
     * @return
     */
    public List<Role> queryAll() {
        try {
            return mapper.queryAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过id获取角色
     * @param id
     * @return
     */
    public Role get(String id) {
        try {
            return mapper.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 新增角色
     * @param role
     */
    public void insert(Role role) {
        try {
            mapper.insert(role);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 新增角色模块关系
     * @param id
     * @param modules
     */
    public void insertRoleModule(String id, List<Module> modules) {
        try {
            mapper.insertRoleModule(id, modules);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 更新角色
     * @param role
     */
    public void update(Role role) {
        try {
            mapper.update(role);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 删除角色
     * @param id
     */
    public void delete(String id) {
        try {
            mapper.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 删除角色用户关系
     * @param id
     */
    public void deleteRoleUser(String id) {
        try {
            mapper.deleteRoleUser(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 删除角色模块关系
     * @param id
     */
    public void deleteRoleModule(String id) {
        try {
            mapper.deleteRoleModule(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过角色名获取角色总数
     * @param role
     * @return
     */
    public long countByRoleName(Role role) {
        try {
            return mapper.countByRoleName(role);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        }
    }
}