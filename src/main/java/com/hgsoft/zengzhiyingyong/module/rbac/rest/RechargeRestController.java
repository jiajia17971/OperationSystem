package com.hgsoft.zengzhiyingyong.module.rbac.rest;


import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ConsumeEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.RechargeEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.service.ConsumeService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.RechargeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WQP on 2018/7/31.
 * 充值 restful控制器
 */
@RestController
@RequestMapping("/api/v1/recharge")
public class RechargeRestController extends BasePageController<RechargeEntity>{

    private Logger logger = LoggerFactory.getLogger(RechargeRestController.class);
    private int voucherid;

    @Autowired
    RechargeService rechargeService;


    /**
     * 长款流水分页查询
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public  Map<String, Object> query(String page, String limit, String faceCardNum,String createTime) {
        String startTime = "";
        String endTime = "";
        if(StringUtils.isNotBlank(createTime)){
            startTime = createTime.split(" - ")[0];
            endTime = createTime.split(" - ")[1];
        }
        Page<RechargeEntity> pages = super.getPage(page, limit);
        Page<RechargeEntity> pg = rechargeService.query( pages,faceCardNum,startTime,endTime);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", pg.getTotalCount());
        result.put("data", pg.getResult());
        return result;
    }





}