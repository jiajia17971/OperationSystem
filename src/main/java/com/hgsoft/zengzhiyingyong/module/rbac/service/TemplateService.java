package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.*;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by WQP on 2018/7/30.
 * word模板Service
 */
@Service
@Transactional
public class TemplateService {
    @Autowired
    VoucherDao voucherDao;

    private Configuration configuration = null;
    public TemplateService(){
            configuration = new Configuration();
                configuration.setDefaultEncoding("UTF-8");
           }


    public static void main(String[] args){

        TemplateService templateService = new TemplateService();
        templateService.createWord("39");

    }


    public void createWord(String voucherid){
        Map<String,Object> dataMap=new HashMap<String,Object>();
        getData(voucherid);
        String path = this.getClass().getClassLoader().getResource("").getPath();
        path = path.substring(0,path.indexOf("/target"))+"/src/main/webapp/template";
        configuration.setClassForTemplateLoading(this.getClass(), path);//模板文件所在路径
        Template t=null;
        try {
        t = configuration.getTemplate("vouchertemplate.ftl"); //获取模板文件
         } catch (IOException e) {
               e.printStackTrace();
               }
                File outFile = new File("D:/outFile"+Math.random()*10000+".doc"); //导出文件
                Writer out = null;
                 try {
                        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

               try {
                       t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
                 } catch (Exception e) {
                       e.printStackTrace();
                   }
             }
    private Map<String, Object> getData(String voucherid) {
//            Voucher exvoucher =  voucherDao.getVoucherById(voucherid);

            Map<String, Object> dataMap = new HashMap<String, Object>();
             dataMap.put("voucherid", "ccccccccc");
             dataMap.put("theme", "主題");
             dataMap.put("organization", "机构");
             dataMap.put("ri", "6");
             dataMap.put("shenheren", "lc");
         List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
             for (int i = 0; i < 10; i++) {
                     Map<String,Object> map = new HashMap<String,Object>();
                     map.put("xuehao", i);
                     map.put("neirong", "内容"+i);
                     list.add(map);
                 }


             dataMap.put("list", list);
             return dataMap;
             }



}