package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.CardDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.ReloadDao;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.RetransDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ReloadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by WQP on 2018/7/30.
 * 文件重发项Service
 */
@Service
@Transactional(value = "transactionManager")
public class ReloadService {

    private Logger logger = LoggerFactory.getLogger(ReloadService.class);


    @Autowired
    private ReloadDao reloadDao;
    @Autowired
    private RetransDao retransDao;

    /**
     * 查询文件重发列表
     * @return
     */
    public Page<ReloadEntity> query(Page<ReloadEntity> page , int voucherid) {

        return reloadDao.query(page,voucherid);
    }

    /**
     * 保存重发项
     * @return
     */
    public int save(ReloadEntity reload) {
        if(reload!=null){
            reload.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(new Date()));
//            CL_EexitSum查询当前批次号数量和金额
           ReloadEntity eReloadEntity =  searchSummary(reload.getBatchNo(),reload.getOrgid());
           if(eReloadEntity!=null){
               reload.setEExtiNum(eReloadEntity.getEExtiNum());
               reload.setEExtiMoney(eReloadEntity.getEExtiMoney().multiply(new BigDecimal(100)));
           }
        }
        return reloadDao.save(reload);
    }

    /**
     * 修改重发项
     * @return
     */
    public int update(ReloadEntity reload) {
//        ReloadEntity eReloadEntity =  reloadDao.get(reload.getId());
        return reloadDao.update(reload);
    }

    public ReloadEntity searchSummary(String batchno,String orgid){

//        return reloadDao.searchSummary(batchno,orgid);
        return retransDao.searchSummary(batchno,orgid);
    }

    /**
     * 删除重发项
     * @return
     */
    public boolean delete(String id) {
        return reloadDao.delete(id);
    }

    /**
     * 根据id获取重发项
     * @param id
     * @return
     */
    public ReloadEntity get(String id){
        return reloadDao.get(id);
    }

    /**
     *
     * @param voucherid
     * @return
     */
    public List<ReloadEntity> searchByVoucher(String voucherid){

        return reloadDao.searchByVoucher(voucherid);
    }




}