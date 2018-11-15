package com.hgsoft.zengzhiyingyong.module.rbac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hegc on 2016/4/7.
 * 功能模块Controller控制器
 */
@Controller
@RequestMapping("/module")
public class ModuleController {

    private Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @RequestMapping("/edit")
    public String edit() {
        return "kkgl/module/edit";
    }
}