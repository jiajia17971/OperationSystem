package com.hgsoft.zengzhiyingyong.module.rbac.rest;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import com.hgsoft.zengzhiyingyong.module.rbac.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by WQP on 2018/8/31.
 * reload restful控制器
 */
@RestController
@RequestMapping("/api/v1/reload")
public class ReloadRestController extends BasePageController<ReloadEntity>{

    private Logger logger = LoggerFactory.getLogger(ReloadRestController.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private ReloadService reloadService;

    @Autowired
    private RestrainService restrainService;

    @Autowired
    private BusinessService businessService;

    public static int failedNum = 0;
    public static int successNum = 0;
    public static int totalNum = 0;
    public static List<String> list = new ArrayList<String>();


    @RequestMapping(value = "/query",method =RequestMethod.GET )
    public Map<String, Object>  query(String page, String limit, String voucherid) throws IOException {

        Page<ReloadEntity> pages = super.getPage(page, limit);
        Page<ReloadEntity> pg = reloadService.query( pages,Integer.parseInt(voucherid));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", pg.getTotalCount());
        result.put("data", pg.getResult());
        return result;
    }

    @RequestMapping(value = "/save",method =RequestMethod.POST )
    public int  save(ReloadEntity reload) throws IOException {

        try {
            int num = 0;
            logger.info("反序列化的card为 :" + reload);
            if(StringUtils.isEmpty(reload.getId())){

                num = reloadService.save(reload);
            }else{

                num = reloadService.update(reload);
            }
            return num;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return  0;
        }
    }
    @RequestMapping(value = "/delete/{id}",method =RequestMethod.GET )
    public boolean  delete(@PathVariable String id) throws IOException {

        try {
            return  reloadService.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    @RequestMapping(value = "/search",method =RequestMethod.POST )
    public ReloadEntity  search(ReloadEntity reload) throws IOException {

        try {
            return  reloadService.searchSummary(reload.getBatchNo(),reload.getOrgid());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 执行记账文件重发ctl
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/toRestrain/{voucherid}",method =RequestMethod.GET )
    public ResponseEntity  toRestrain(@PathVariable("voucherid") String voucherid) throws IOException {
        if(voucherid==null){
            return null;
        }
        ResponseEntity res = new ResponseEntity();
        try {
        List<ReloadEntity> reloadList =  reloadService.searchByVoucher(voucherid);
        totalNum = reloadList.size();
        if(reloadList==null||reloadList.size()==0){
            return null;
        }else{
            for(int i=0;i<reloadList.size();i++) {
                ReloadEntity reload = reloadList.get(i);
                boolean b = restrainService.restrain(reload);
                if (b) {
                    reload.setStatus(1);
                    reloadService.update(reload);
                    successNum++;
                } else {
                    failedNum++;
                    list.add(reload.getBatchNo());
                }
                res.setTotalNum(totalNum);
                if(successNum>0){
                    res.setResponse(true);
                }
                res.setFailedNum(failedNum);

            }
            //记录操作日志(执行重发)
            Business business = new Business();
            business.setOperator(SecurityContextHolder.getUser().getId());
            business.setOptTime(sdf.format(new Date()));
            business.setBusinessType(3);
            business.setSource(voucherid);
            businessService.save(business);

            //更改开库单状态
            Voucher v = new Voucher();
            v.setId(voucherid);
            v.setStatus("2");
            voucherService.save(v);//存在id，更新开库单

            return res;
        }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }




}