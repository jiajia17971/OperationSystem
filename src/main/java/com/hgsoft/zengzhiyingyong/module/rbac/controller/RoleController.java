package com.hgsoft.zengzhiyingyong.module.rbac.controller;

import com.hgsoft.zengzhiyingyong.module.rbac.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hegc on 2016/4/4.
 * 角色Controller
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @RequestMapping("/list")
    public String list() {
        return "kkgl/role/list";
    }

    @RequestMapping("/add")
    public String add() {
        return "kkgl/role/edit";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("rolemap", roleService.get(id));
        return "kkgl/role/edit";
    }
}