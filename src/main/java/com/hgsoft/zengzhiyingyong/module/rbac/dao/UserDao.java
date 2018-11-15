package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.UserMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hegc on 2016/4/2.
 * 用户DAO服务类
 */
@Repository
public class UserDao {

    private Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private UserMapper mapper;

    /**
     * 分页查询
     * @param user
     * @param page
     * @return
     */
    public Page<User> query(User user, Page<User> page){
        try {
            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();
            List<User> results = mapper.query(user, skip, size);
            if(results!=null&&results.size()>0){
                for (int i = 0;i<results.size();i++) {
                    if(results.get(i).getStatus()==0){

                    }
                }
            }
            long count = mapper.count(user);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    public User get(String id) {
        try {
            return mapper.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 通过登录名获取用户信息
     * @param loginName
     * @return
     */
    public User getByLoginName(String loginName) {
        try {
            return mapper.getByLoginName(loginName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 启用禁用用户
     * @param id
     * @param status
     */
    public void enable(String id, int status) {
        try {
            mapper.enable(id, status);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 更新登录时间
     * @param loginName
     */
    public void updateLoginTime(String loginName) {
        mapper.updateLoginTime(loginName);
    }

    /**
     * 新增用户
     * @param user
     */
    public void insert(User user) {
        try {
            mapper.insert(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 新增用户角色关系
     * @param id
     * @param roles
     */
    public void insertUserRole(String id, List<Role> roles) {
        try {
            mapper.insertUserRole(id, roles);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 更新用户
     * @param user
     */
    public void update(User user) {
        try {
            mapper.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 批量删除用户
     * @param ids
     */
    public void delete(String[] ids) {
        try {
            mapper.deleteBatch(ids);
            for(String id : ids) {
                mapper.deleteUserRole(id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除用户<删除用户角色>
     * @param id
     */
    public void delete(String id) {
        try {
            mapper.delete(id);
            mapper.deleteUserRole(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除用户角色
     * @param id 用户id
     */
    public void deleteUserRole(String id) {
        try {
            mapper.deleteUserRole(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 校验用户名是否重复
     * @param user
     * @return
     */
    public long countByLoginName(User user) {
        try {
            return mapper.countByLoginName(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}