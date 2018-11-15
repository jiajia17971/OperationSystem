package com.hgsoft.zengzhiyingyong.module.rbac.controller;

import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by hegc on 2016/4/2.
 * 用户Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list() {
        logger.info("into");
        return "kkgl/user/list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        Map<String, Object> exuser = userService.get(null);
        model.addAttribute("usermap", exuser);
        model.addAttribute("type","add");
        return "kkgl/user/edit";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")String id, Model model) {
        Map<String, Object> exuser = userService.get(id);
        model.addAttribute("usermap", exuser);
        User u = (User) exuser.get("user");
        List<Role> li = u.getRoles();
        for(int i=0;i<li.size();i++){
            Role r =(Role)li.get(i);
            if(r.getId().equals("admin")){
                model.addAttribute("usermode","admin");
                break;
            }
        }
        model.addAttribute("type","edit");
        return "kkgl/user/edit";
    }
}