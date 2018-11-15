package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.context.MultipleDataSource;
import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.ReloadMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.RoleMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.VoucherMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/30.
 * 记账文件重发DAO服务类
 */
@Repository
public class ReloadDao {

    private Logger logger = LoggerFactory.getLogger(ReloadDao.class);
    @Autowired
    private ReloadMapper reloadMapper;
    @Autowired
    private VoucherMapper voucherMapper;
    @Autowired
    private RoleMapper roleMapper;

    public Page<ReloadEntity> query(Page<ReloadEntity> page, int voucherid){
        try {
            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();
            List<ReloadEntity> results = reloadMapper.query(skip, size,voucherid);
            Voucher v = voucherMapper.get(String.valueOf(voucherid));
            if(v!=null){
                for (ReloadEntity reload:results) {
                    if("1".equals(v.getStatus())){
                        reload.setVstate(1);
                    }else if("5".equals(v.getStatus())){
                        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityContextHolder.USER_CONTEXT);
                        User eu = new User();
                        if(user!=null&& user.getRoles()!=null ){//超级管理员
                            List<Role> list = user.getRoles();
                            for(int i = 0;i<list.size();i++){
                                Role r =  (Role)list.get(i);
                                Role re = (Role)roleMapper.get(r.getId());
                                if("checkrole".equals(re.getId())){
                                    reload.setVstate(2);
                                    break;
                                }else{
                                    reload.setVstate(1);
                                }
                            }


                        }

                    }else{
                        reload.setVstate(2);
                    }

                }
            }
            long count = reloadMapper.count(voucherid);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }

    /*public ReloadEntity searchSummary(String batchno,String orig){
       return  reloadMapper.searchSummary(batchno,orig);
    }*/

    /**
     * 保存重发项
     * @param reload
     */
    public int save(ReloadEntity reload) {
        try {

            return reloadMapper.save(reload);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }

    /**
     * 保存重发项
     * @param reload
     */
    public int update(ReloadEntity reload) {
        try {
            MultipleDataSource.setDataSourceKey("dataSource");
            return reloadMapper.update(reload);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }

    public boolean delete (String id){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        try {
            MultipleDataSource.setDataSourceKey("dataSource");
            return   reloadMapper.delete(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 根据id获取重发项信息
     * @param id
     * @return
     */
    public ReloadEntity get(String id){
        try {
           return reloadMapper.get(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<ReloadEntity> searchByVoucher(String voucherid){

        return reloadMapper.query(0, 9999,Integer.parseInt(voucherid));

    }


}