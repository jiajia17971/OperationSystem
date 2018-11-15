package com.hgsoft.zengzhiyingyong.module.rbac.controller;
/**
 * 业务日志控制器
 */

import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business")
public class BusinessController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/list")
    public String list() {

        return "kkgl/business/list";
    }

}
