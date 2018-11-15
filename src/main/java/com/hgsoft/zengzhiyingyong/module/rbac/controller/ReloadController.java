package com.hgsoft.zengzhiyingyong.module.rbac.controller;
/**
 * 重发项控制器
 */

import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ReloadEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.UploadEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import com.hgsoft.zengzhiyingyong.module.rbac.service.CardService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.ReloadService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UploadService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reload")
public class ReloadController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    VoucherService voucherService;
    @Autowired
    ReloadService reloadService;
    @Autowired
    UploadService uploadService;

    @RequestMapping("/info")
    public String info() {
        logger.info("into");

        return "kkgl/reuploads/info";
    }

    @RequestMapping("/cardlist/{id}")
    public String cardlist(@PathVariable("id")String id, Model model) {
        logger.info("into");
        model.addAttribute("voucherid",id);
        Voucher v = voucherService.getVoucherById(id);
        model.addAttribute("voucher",v);
        return "kkgl/reuploads/list";
    }

    @RequestMapping("/info/{id}/{voucherid}")
    public String info(@PathVariable("id")String id,@PathVariable("voucherid")String voucherid, Model model) {
        if(StringUtils.isEmpty(id)){
            return "kkgl/reuploads/list";
        }
        List<UploadEntity> filelist = null;
        ReloadEntity reload = reloadService.get(id);
        if(!StringUtils.isEmpty(voucherid)){
            filelist = uploadService.findAttaches(Integer.parseInt(voucherid));
        }
        model.addAttribute("filelist",filelist);
        model.addAttribute("reload",reload);

        return "kkgl/reuploads/info";
    }

    @RequestMapping("/add/{id}")
    public String add(@PathVariable("id")String id, Model model) {
        Voucher exvoucher =  voucherService.getVoucherById(id);
        if(exvoucher!=null){
            model.addAttribute("voucherid", id);
        }else{
            model.addAttribute("voucherid", null);
        }

        return "kkgl/reuploads/info";
    }

}
