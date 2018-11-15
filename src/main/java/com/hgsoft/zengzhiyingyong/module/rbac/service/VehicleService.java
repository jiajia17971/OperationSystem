package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ModuleDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.VehicleDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.VoucherDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Vehicle;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WQP on 2018/7/30.
 * 模块Service
 */
@Service
@Transactional(value="otherTransactionManager")
public class VehicleService {

    private Logger logger = LoggerFactory.getLogger(VehicleService.class);

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private VoucherService voucherService;

    /**
     * 查询卡信息
     * @param faceCardNum
     * @return
     */
    public List<Vehicle> findByCardNum(String faceCardNum){
        List<Vehicle> list = new ArrayList<Vehicle>();
        List<Vehicle> li1 = vehicleDao.findByFaceCardNum(faceCardNum);
        List<Vehicle> li2 = vehicleDao.findByCCBCardNum(faceCardNum);
        if(li1!=null&&li1.size()>0){
            list.addAll(li1);
        }
        if(li2!=null&&li2.size()>0){
            list.addAll(li2);
        }
        return list;
    }

    /**
     * 更新最后修改时间
     * @param faceCardNum
     * @return
     */
    public boolean handleTime(String faceCardNum){

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityContextHolder.USER_CONTEXT);
        List<Vehicle> li1 = vehicleDao.findByFaceCardNum(faceCardNum);
        List<Vehicle> li2 = vehicleDao.findByCCBCardNum(faceCardNum);
        if(li1!=null&&li1.size()>0){
            Vehicle veh = li1.get(0);
            System.out.print(veh.getFaceCardNum()+"+++++++++++++");
            int b =vehicleDao.updateCard(veh);
            System.out.print(b+"===============================");
            if(b>0){
                //在开库单表中插入一条记录
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
                Voucher v = new Voucher();
                v.setStatus("2");
                v.setType(4);
                v.setOwner(user.getId());
                v.setCreTime(sdf.format(new Date()));
                v.setProcessTime(sdf.format(new Date()));
                v.setEndTime(sdf.format(new Date()));
                v.setTheme("跨行办理处理");
                v.setDescription("跨行办理30天");
                v.setOrgnization("0");
                v.setVoucherid("khbl0000");
                voucherService.save(v);
                return true;
            }else{
                return false;
            }

        }
        if(li2!=null&&li2.size()>0){
            System.out.print("***********************");
            Vehicle veh = li2.get(0);
            int b = vehicleDao.updateCCBCard(veh);
            if(b>0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
                Voucher v = new Voucher();
                v.setStatus("1");
                v.setType(4);
                v.setOwner(user.getId());
                v.setCreTime(sdf.format(new Date()));
                v.setProcessTime(sdf.format(new Date()));
                v.setEndTime(sdf.format(new Date()));
                v.setDescription("跨行办理30天");
                v.setTheme("跨行办理处理");
                v.setOrgnization("0");
                v.setVoucherid("khbl0000");
                voucherService.save(v);
                return true;
            }else{
                return false;
            }

        }

        return  false;

    }

    public String findCard(String faceCardNum,String vehiclePlate,String vehicleColor){

        return vehicleDao.findCard(faceCardNum,vehiclePlate,vehicleColor);
    }




}