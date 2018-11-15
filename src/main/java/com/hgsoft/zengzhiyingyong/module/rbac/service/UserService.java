package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.UserDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.util.Identities;
import com.hgsoft.zengzhiyingyong.util.MD5Coder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by hegc on 2016/4/3.
 * 用户Service
 */
@Service
@Transactional
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    /**
     * 分页获取用户信息
     * @param user
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public Page<User> query(User user, Page<User> page) {
        return userDao.query(user, page);
    }

    /**
     * 通过id获取用户信息<冗余用户选择的角色>
     * @param id 用户id
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> get(String id) {
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> roleParams = Lists.newArrayList();
        map.put("roles", roleParams);
        List<Role> roleList = roleService.queryAll();
        if(StringUtils.isBlank(id)) {
            map.put("user", new User());
            if(CollectionUtils.isEmpty(roleList)) {
                map.put("roles", roleParams);
            } else {
                for(Role r : roleList) {
                    Map<String, Object> param = Maps.newHashMap();
                    param.put("id", r.getId());
                    param.put("name", r.getName());
                    param.put("status", r.getStatus()); //角色状态, 0-非默认,1-默认
                    param.put("check", false);
                    roleParams.add(param);
                }
            }
        } else {
            User user = userDao.get(id);
            map.put("user", user);
            if(CollectionUtils.isEmpty(roleList)) {
                map.put("roles", roleParams);
            } else {
                for(Role r : roleList) {
                    Map<String, Object> param = Maps.newHashMap();
                    param.put("id", r.getId());
                    param.put("name", r.getName());
                    param.put("status", r.getStatus()); //角色状态, 0-非默认,1-默认
                    if(CollectionUtils.isEmpty(user.getRoles())) {
                        param.put("check", false);
                    } else {
                        boolean flag = false;
                        for(Role ur : user.getRoles()) {
                            if(ur.getId().equals(r.getId())) {
                                flag = true;
                                break;
                            }
                        }
                        param.put("check", flag);
                    }
                    roleParams.add(param);
                }
            }
        }
        return map;
    }

    /**
     * 保存用户
     * @param user
     */
    public void save(User user) {
        if(StringUtils.isBlank(user.getId())) {
            this.insert(user);
        } else {
            this.update(user);
        }
    }

    private void insert(User user) {
        String uuid = Identities.uuid();
        user.setId(uuid);
        String newPassword = MD5Coder.encodeLoginUser(user.getLoginName().trim(), user.getPassword());
        user.setPassword(newPassword);
        userDao.insert(user);
        logger.info("新增用户，id为：{}", uuid);
        if(CollectionUtils.isNotEmpty(user.getRoles())) {
            userDao.insertUserRole(uuid, user.getRoles());
        }
    }

    private void update(User user) {
        User u = userDao.get(user.getId());
        String newPassword = MD5Coder.encodeLoginUser(u.getLoginName().trim(), user.getPassword());
        user.setPassword(newPassword);
        userDao.update(user);
        String id = user.getId();
        userDao.deleteUserRole(id);
        if(CollectionUtils.isNotEmpty(user.getRoles())) {
            userDao.insertUserRole(id, user.getRoles());
        }
    }

    public void delete(String id) {
        userDao.delete(id);
    }

    /**
     * 更新登录时间
     * @param loginName
     */
    public void updateLoginTime(String loginName) {
        userDao.updateLoginTime(loginName);
    }

    /**
     * 校验登录名是否重复
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkLoginName(User user) {
        return userDao.countByLoginName(user) == 0;
    }

    /**
     * 用户登录<校验登录名和密码>
     * @param loginName
     * @param password
     * @return
     */
    @Transactional(readOnly = true)
    public User login(String loginName, String password) {
        User user = userDao.getByLoginName(loginName);
        if(ObjectUtils.notEqual(user, null)) {
            String dbPassword = user.getPassword();
            String encodePassword = MD5Coder.encodeLoginUser(loginName, password);
            if(!StringUtils.equals(dbPassword, encodePassword)) {
                return null;
            }
        }
        return user;
    }
}