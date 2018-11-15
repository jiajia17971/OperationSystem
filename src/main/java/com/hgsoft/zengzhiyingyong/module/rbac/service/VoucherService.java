package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.BusinessDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.CardDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ModuleDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.VoucherDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
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
@Transactional(value = "transactionManager")
public class VoucherService {

    private Logger logger = LoggerFactory.getLogger(VoucherService.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private VoucherDao voucherDao;
    @Autowired
    private BusinessDao businessDao;
    @Autowired
    private CardDao cardDao;
    @Autowired
    private RestrainService restrainService;

    @Autowired
    private CardService cardService;
    @Autowired
    RoleService roleService;




    /**
     * 获取开库单
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Voucher> query( Page<Voucher> page ,Voucher voucher) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityContextHolder.USER_CONTEXT);
        User eu = new User();
        if(user!=null&& user.getRoles()!=null ){//超级管理员
            List<Role> list = user.getRoles();
            for(int i = 0;i<list.size();i++){
               Role r =  (Role)list.get(i);
                Role re = (Role)roleService.get(r.getId()).get("role");
                if("admin".equals(r.getId())){
                    eu.setId(null);

                    break;
                }else if("checkrole".equals(re.getId())){
//                    voucher.setStatus("9");
                    break;
                }else{
                    eu.setId(user.getId());
                }
            }


        }
        String start = "";
        String end = "";
        if(StringUtils.isNotBlank(voucher.getCreTime())){
            String[] datestr = voucher.getCreTime().split(" - ");
            start = datestr[0];
            end = datestr[1];
        }
        return voucherDao.query(eu,page,voucher,start,end);
    }

    /**
     * 获取开库单
     * @return
     */
    @Transactional(readOnly = true)
    public boolean delete( String id) {

        boolean b = voucherDao.delete(id);

        return b;
    }
    /**
     * 根据id获取开库单
     * @return
     */
    @Transactional(readOnly = true)
    public Voucher getVoucherById(String id) {

        return voucherDao.getVoucherById(id);
    }
    /**
     * 根据id获取开库单
     * @return
     */
    @Transactional(readOnly = true)
    public Voucher getVoucherByCode(String voucherid) {

        return voucherDao.getVoucherByCode(voucherid);
    }
    /**
     * 根据id获取开库单
     * @return
     */
    @Transactional(readOnly = true)
    public Voucher getVoucherInfo(String id) {

        return voucherDao.getVoucherInfo(id);
    }
    /**
     * 保存开库单
     * @return
     */

    public int save( Voucher voucher) {

        if(voucher!=null){

            if(voucher.getId()==null||"".equals(voucher.getId())){

                int i =  voucherDao.save(voucher);

                //记录操作日志(执行重发)
                Business business = new Business();
                business.setOperator(SecurityContextHolder.getUser().getId());
                business.setOptTime(sdf.format(new Date()));
                business.setBusinessType(voucher.getType());
                business.setSource(voucher.getId());
                businessDao.save(business);
                return i;
            }else{
                return voucherDao.update(voucher);
            }

        }else{
            return 0;
        }

    }

    /**
     * 生成vouchercode(voucherid)
     * @return
     */
    public String generateVC(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        return voucherDao.generateVC();
        String prefix = "HB42-ETC-DBRM";
        String datestr = "-" + sdf.format(new Date());
        String voucherid  = prefix + datestr +"-"+Math.ceil(Math.random()*1000000);
        return voucherid.substring(0,voucherid.indexOf('.'));
    }

    public int sendcheck(Voucher voucher){
         return  voucherDao.update(voucher);
    }


}