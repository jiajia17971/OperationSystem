package com.hgsoft.zengzhiyingyong.module.rbac.controller;
/**
 * 开库单控制器
 */

import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recharge")
public class ChargeController {
    private Logger logger = LoggerFactory.getLogger(ChargeController.class);
    @Autowired
    VoucherService voucherService;
    @RequestMapping("/list/{faceCardNum}")
    public String list(@PathVariable("faceCardNum")String faceCardNum, Model model) {//此处faceCardNum为调账开库单上的号码
        model.addAttribute("faceCardNum",faceCardNum);


        return "kkgl/flow/chargelist";
    }




}
