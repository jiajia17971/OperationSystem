package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.google.common.collect.Maps;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.RoleDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.security.realm.AuthControllerImpl;
import com.hgsoft.zengzhiyingyong.util.Identities;
import org.apache.commons.collections.CollectionUtils;
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
 * 角色Service
 */
@Service
@Transactional
public class RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private AuthControllerImpl authController;

    /**
     * 分页查询
     * @param role
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Role> query(Role role, Page page) {
        return roleDao.query(role, page);
    }

    /**
     * 查询所有角色
     * @return
     */
    @Transactional(readOnly = true)
    public List<Role> queryAll() {
        return roleDao.queryAll();
    }

    /**
     * 删除角色(删除用户角色, 角色模块关系)
     * @param id
     */
    public void delete(String id) {
        roleDao.delete(id);
        roleDao.deleteRoleModule(id);
        roleDao.deleteRoleUser(id);
    }

    public void save(Role role) {
        String roleId = role.getId();
        if(StringUtils.isBlank(roleId)) {
            roleId = Identities.uuid();
            role.setId(roleId);
            roleDao.insert(role);
        } else {
            roleDao.deleteRoleModule(roleId);
            roleDao.update(role);
        }

        if(CollectionUtils.isNotEmpty(role.getModules())) {
            roleDao.insertRoleModule(roleId, role.getModules());
        }
        //保存完成后, 重新加载权限数据
        authController.reCreateFilterChains();
    }

    /**
     * 获取角色
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> get(String id) {
        Map<String, Object> map = Maps.newHashMap();
        //存放模块数据集
        map.put("tree", moduleService.getTreeData());
        if(StringUtils.isBlank(id)) {
            map.put("role", new Role());
        } else {
            Role role = roleDao.get(id);
            map.put("role", role);
        }
        return map;
    }

    /**
     * 判断角色名是否重复
     * @param role
     * @return true 不重复, false 重复
     */
    public boolean checkRoleName(Role role) {
        return roleDao.countByRoleName(role) == 0;
    }
}