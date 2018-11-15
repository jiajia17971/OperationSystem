package com.hgsoft.zengzhiyingyong.module.rbac.rest;


import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Vehicle;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import com.hgsoft.zengzhiyingyong.module.rbac.service.CardService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UploadService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VehicleService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;

import com.hgsoft.zengzhiyingyong.util.WordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by WQP on 2018/7/31.
 * Voucher restful控制器
 */
@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherRestController extends BasePageController<Voucher>{

    private Logger logger = LoggerFactory.getLogger(VoucherRestController.class);
    private int voucherid;

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private CardService cardService;



//    @Autowired
//    private TemplateService templateService;


    /**
     * 开库单分页查询
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public  Map<String, Object> query( String page, String limit,String voucherid,String voucheridcode,String type,String creTime,String status,String faceCardNum,String vehiclePlate,String vehicleColor) {


        Voucher v = new Voucher();
        if(StringUtils.isNotEmpty(faceCardNum)||StringUtils.isNotEmpty(vehiclePlate)){
            if(StringUtils.isNotEmpty(vehiclePlate)){
                try{
                    vehiclePlate = URLDecoder.decode(vehiclePlate,"UTF-8");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
           String cardno =  vehicleService.findCard(faceCardNum,vehiclePlate,vehicleColor);
            Integer[] empty = {0,0};
           if(StringUtils.isNotEmpty(cardno)){
               List<Card> clist =   cardService.getVoucherByCardNo(cardno);
               List<Integer> slist = new ArrayList<Integer>();
               for (int i=0;i<clist.size();i++) {
                   slist.add(Integer.parseInt(clist.get(i).getVoucherid()));
               }
             v.setConditions(slist.size()>0?slist.toArray(new Integer[clist.size()]) :empty);
           }else{
               v.setConditions(empty);
           }

        }

        v.setVoucherid(voucherid);
        if(StringUtils.isNotBlank(type)){
            v.setType(Integer.parseInt(type));
        }
        v.setCreTime(creTime);
        v.setStatus(status);
        Page<Voucher> pages = super.getPage(page, limit);
        Page<Voucher> pg = voucherService.query( pages,v);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", pg.getTotalCount());
        result.put("data", pg.getResult());
        return result;
    }



    /**
     * 删除开库单
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public boolean delete(@PathVariable("id")String id) {
        try {
           return  voucherService.delete(id);


        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 保存开库单
     * @param voucher
     * @return
      */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public int save(Voucher voucher) {
        try {
            logger.info("反序列化的voucher为 :" + voucher);
            if(StringUtils.isEmpty(voucher.getStatus())){
                voucher.setStatus("1");
            }
            int num = voucherService.save(voucher);
            if(num<=0){
                return 0;
            }
            int id =Integer.parseInt(voucher.getId()) ;
            voucherid = id;//保存开库单id
               String atts = voucher.getAttachment();
               if(atts!=null){
                 String[] arr =   atts.split(",");
                   for (String a:arr) {
                       uploadService.update(id,arr);
                   }
               }
                return voucherid;


        } catch (Exception e) {
            logger.error(e.getMessage());
            return voucherid;
        }

    }

    /**
     * 跨行30天办理快捷(查询)
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/dayshandle", method = RequestMethod.GET)
    public Map<String, Object> dayshandle(String faceCardNum,String page, String limit) {

        List<Vehicle> list = vehicleService.findByCardNum(faceCardNum);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", 1);
        result.put("data", list);
        return  result;

    }

    /**
     * 跨行30天办理快捷(办理)
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/handleTime", method = RequestMethod.GET)
    public boolean handleTime(String faceCardNum) {
        if(StringUtils.isNotBlank(faceCardNum)){
            return  vehicleService.handleTime(faceCardNum);
        }else{
            return false;
        }

    }



}