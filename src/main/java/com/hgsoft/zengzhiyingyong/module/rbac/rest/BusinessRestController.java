package com.hgsoft.zengzhiyingyong.module.rbac.rest;

import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Business;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.UploadEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import com.hgsoft.zengzhiyingyong.module.rbac.service.BusinessService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UploadService;
import com.hgsoft.zengzhiyingyong.util.UUIDUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WQP on 2018/7/31.
 * Business restful控制器
 */
@RestController
@RequestMapping("/api/v1/business")
public class BusinessRestController extends BasePageController<Business>{

    private Logger logger = LoggerFactory.getLogger(BusinessRestController.class);


    @Autowired
    private BusinessService businessService;



    @RequestMapping(value = "/query",method =RequestMethod.POST )
    public Map<String, Object>  query(String page, String limit, Business business, String statTime, String endTime) throws IOException {

        Page<Business> pages = super.getPage(page, limit);
        Page<Business> pg = businessService.query( pages,business,statTime,endTime);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", pg.getTotalCount());
        result.put("data", pg.getResult());
        return result;
    }




}