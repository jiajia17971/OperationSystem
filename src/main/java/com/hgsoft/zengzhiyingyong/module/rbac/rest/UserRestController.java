package com.hgsoft.zengzhiyingyong.module.rbac.rest;

import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hegc on 2016/4/2.
 * User restful控制器
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController extends BasePageController<User>{

    private Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户分页查询
     * @param user
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Map<String, Object> query(User user,String page, String limit) {
        Page<User> p = super.getPage(page, limit);
        Page<User> pg =  userService.query(user, p);
        Map<String, Object> result = new HashMap<String, Object>();
        List res = pg.getResult();

        result.put("code", 0);
        result.put("msg", "");
        result.put("count", pg.getTotalCount());
        result.put("data", pg.getResult());
        return result;
    }

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, Object> get(String id) {
        return userService.get(id);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public boolean delete(@PathVariable("id")String id) {
        try {
            userService.delete(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean save(@RequestBody User user) {
        try {
            System.out.println("反序列化的user为 :" + user);
            userService.save(user);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 校验用户名是否存在
     * @param user
     * @return true不存在, false存在
     */
    @RequestMapping(value = "/checkLoginName", method = RequestMethod.POST)
    public boolean checkLoginName(User user) {
        try {
            return userService.checkLoginName(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}