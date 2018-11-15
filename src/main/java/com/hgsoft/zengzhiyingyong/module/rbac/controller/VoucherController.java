package com.hgsoft.zengzhiyingyong.module.rbac.controller;
/**
 * 开库单控制器
 */

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UserService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;
import com.hgsoft.zengzhiyingyong.util.WordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/voucher")
public class VoucherController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    VoucherService voucherService;
    @Autowired
    UserService userService;

    @RequestMapping("/list")
    public String list(Model model) {
        logger.info("into");
        model.addAttribute("user",userService.get(SecurityContextHolder.getUser().getId()));
        return "kkgl/voucher/list";
    }
    @RequestMapping("/checklist")
    public String checklist(Model model) {
        logger.info("into");
        model.addAttribute("user",userService.get(SecurityContextHolder.getUser().getId()));
        return "kkgl/voucher/checklist";
    }

    @RequestMapping("/info")
    public String info(Model model) {
        logger.info("into");
        String voucherid = voucherService.generateVC();
        Voucher voucher = new Voucher();
        voucher.setVoucherid(voucherid);
        model.addAttribute("type","add");
        model.addAttribute("voucherid",voucherid);
        return "kkgl/voucher/info";
    }
    @RequestMapping("/completeInfo")
    public String completeInfo(Model model) {
        logger.info("completeInfo");
        String voucherid = voucherService.generateVC();
        Voucher voucher = new Voucher();
        voucher.setVoucherid(voucherid);
        model.addAttribute("type","add");
        model.addAttribute("voucherid",voucherid);
        return "kkgl/voucher/complateinfo";
    }
    @RequestMapping("/edit")
    public String edit(Model model) {
        logger.info("edit");
        String voucherid = voucherService.generateVC();
        Voucher voucher = new Voucher();
        voucher.setVoucherid(voucherid);
        model.addAttribute("type","edit");
        model.addAttribute("voucherid",voucherid);

        return "kkgl/voucher/info";
    }

    @RequestMapping("/cardlist/{id}")
    public String cardlist(@PathVariable("id")String id, Model model) {
        logger.info("into");
        model.addAttribute("voucherid",id);
        Voucher exvoucher =  voucherService.getVoucherById(id);
        if(exvoucher!=null){
            model.addAttribute("voucher", exvoucher);
            model.addAttribute("user",userService.get(SecurityContextHolder.getUser().getId()));
        }else{
            model.addAttribute("voucher", null);
        }
        return "kkgl/card/list";
    }
    @RequestMapping("/reuploads/{id}")
    public String reuploads(@PathVariable("id")String id, Model model) {
        logger.info("into");
        Voucher exvoucher =  voucherService.getVoucherById(id);
        if(exvoucher!=null){
            model.addAttribute("voucher", exvoucher);
            model.addAttribute("user",userService.get(exvoucher.getOwner()));
        }else{
            model.addAttribute("voucher", null);
        }
        model.addAttribute("voucherid",id);
        return "kkgl/reuploads/list";
    }


    @RequestMapping("/info/{id}")
    public String info(@PathVariable("id")String id, Model model) {
        Voucher exvoucher =  voucherService.getVoucherById(id);
        if(exvoucher!=null){
            model.addAttribute("voucher", exvoucher);
            model.addAttribute("user",userService.get(exvoucher.getOwner()));
        }else{
            model.addAttribute("voucher", null);
        }
        model.addAttribute("type","view");
        return "kkgl/voucher/info";
    }
    @RequestMapping("/completeInfo/{id}")
    public String completeInfo(@PathVariable("id")String id, Model model) {
        Voucher exvoucher =  voucherService.getVoucherById(id);
        if(exvoucher!=null){
            model.addAttribute("voucher", exvoucher);
            model.addAttribute("user",userService.get(exvoucher.getOwner()));
        }else{
            model.addAttribute("voucher", null);
        }
        model.addAttribute("type","view");
        return "kkgl/voucher/completeinfo";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")String id, Model model) {
        Voucher exvoucher =  voucherService.getVoucherById(id);
        if(exvoucher!=null){
            model.addAttribute("voucher", exvoucher);
            model.addAttribute("user",userService.get(exvoucher.getOwner()));
            if(exvoucher.getStatus()=="2"||exvoucher.getStatus()=="0"){//注销、已执行
                model.addAttribute("type","view");
            }else{
                model.addAttribute("type","edit");
            }
        }else{
            model.addAttribute("voucher", null);
            model.addAttribute("type","edit");
        }

        return "kkgl/voucher/info";
    }

    @RequestMapping("/day30handle")
    public String day30handle() {
        logger.info("into");
        return "kkgl/voucher/dayshandle";
    }


    @RequestMapping("/dailylogs")
    public String dailylogs() {
        logger.info("into");
        return "kkgl/voucher/dailylogs";
    }

    /**
     * 打印word模板
     * @param
     */
    @RequestMapping(value = "/printvoucher/{id}", method = RequestMethod.GET)
    public @ResponseBody
    void printvoucher(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {

//        templateService.createWord(voucher.getId());
        Calendar calendar = Calendar.getInstance();// 取当前日期。
        if(id!=null){
            Voucher v = voucherService.getVoucherInfo(id);
            //获得数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("voucherid",v.getVoucherid());
            map.put("organization",v.getOrgnization());
            map.put("theme",v.getTheme());
            map.put("owner",v.getOwner());
            map.put("createTime",v.getCreateTime());
            map.put("endTime",v.getEndTime());
            map.put("description",v.getDescription());
            map.put("createTime",v.getEndTime());
            try {
//                             FreemarkerUtil util =   FreemarkerUtil.getInstance()
                WordUtil.exportMillCertificateWord(request,response,map,"开库申请单"+new SimpleDateFormat("yyyMMddhhmmsss").format(v.getCreateTime()),"vouchertemplate.ftl");
//
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    /**
     * 打印word模板
     * @param
     */
    @RequestMapping(value = "/printreceipt/{id}", method = RequestMethod.GET)
    public  String printreceipt(@PathVariable("id") String id, Model model) {

//        templateService.createWord(voucher.getId());
        Calendar calendar = Calendar.getInstance();// 取当前日期。
        Voucher v = voucherService.getVoucherInfo(id);
//        model.addAttribute("user",userService.get(v.getOwner()));
        model.addAttribute("currentDate",calendar.getTime());
        model.addAttribute("voucher",v);
        return "kkgl/voucher/receipt";
    }


}
