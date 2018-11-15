package com.hgsoft.zengzhiyingyong.module.rbac.controller;
/**
 * 开库单控制器
 */

import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import com.hgsoft.zengzhiyingyong.module.rbac.service.CardService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consume")
public class ConsumeController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    VoucherService voucherService;
    @Autowired
    CardService cardService;
    @RequestMapping("/list/{faceCardNum}/{voucherid}")
    public String list(@PathVariable("faceCardNum")String faceCardNum,@PathVariable("voucherid")String voucherid, Model model) {
        model.addAttribute("faceCardNum",faceCardNum);
        Voucher v =  voucherService.getVoucherById(voucherid);
        if(v!=null){
            model.addAttribute("vtype",v.getType());
        }


        return "kkgl/flow/consumelist";
    }




}
