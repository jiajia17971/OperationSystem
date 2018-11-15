package com.hgsoft.zengzhiyingyong.module.rbac.rest;

import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hegc on 2016/4/4.
 * Role restful控制器
 */
@RestController
@RequestMapping("/api/v1/role")
public class RoleRestController extends BasePageController<Role>{

    private Logger logger = LoggerFactory.getLogger(RoleRestController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 角色分页查询
     * @param role
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Map<String, Object> query(Role role, String pageNo, String pageSize) {
        Page<Role> page = super.getPage(pageNo, pageSize);
        Page<Role> pg = roleService.query(role, page);
        Map<String, Object> result = new HashMap<String, Object>();
        List res = pg.getResult();

        result.put("code", 0);
        result.put("msg", "");
        result.put("count", pg.getTotalCount());
        result.put("data", pg.getResult());
        return result;
    }

    /**
     * 通过id获取角色信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, Object> get(String id) {
        return roleService.get(id);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public boolean delete(@PathVariable("id")String id) {
        try {
            roleService.delete(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean save(@RequestBody Role role) {
        try {
            System.out.println("反序列化的role为 :" + role);
            roleService.save(role);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 校验角色名是否存在
     * @param role
     * @return true不存在, false存在
     */
    @RequestMapping(value = "/checkRoleName", method = RequestMethod.POST)
    public boolean checkRoleName(Role role) {
        try {
            return roleService.checkRoleName(role);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}