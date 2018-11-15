package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.context.MultipleDataSource;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.UserMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.VoucherMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/7/30.
 * 开库单DAO服务类
 */
@Repository
public class VoucherDao {

    private Logger logger = LoggerFactory.getLogger(VoucherDao.class);

    private String voucherid;

    @Autowired
    private VoucherMapper voucherMapper;

    /**
     * 分页查询开库单
     * @param user
     * @param page
     * @return
     */
    public Page<Voucher> query(User user, Page<Voucher> page,Voucher voucher,String start,String end){
        try {
            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();
            List<Voucher> results = voucherMapper.query(user, skip, size,voucher,start,end);
            if("9".equals(voucher.getStatus())){//加载开库单权限 9位审核者 默认为0
                for(int i = 0;i<results.size();i++){
                    results.get(i).setUsertype(1);
                }
            }
            long count = voucherMapper.count(user,voucher,start,end);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 通过id获取开库单
     * @param id
     * @return
     */
    public Voucher getVoucherById(String id) {
        try {
            return voucherMapper.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }
    /**
     * 通过id获取开库单
     * @param voucherid
     * @return
     */
    public Voucher getVoucherByCode(String voucherid) {
        try {
            return voucherMapper.getVoucherByCode(voucherid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }
    /**
     * 通过id获取开库单
     * @param id
     * @return
     */
    public Voucher getVoucherInfo(String id) {
        try {
            return voucherMapper.getInfo(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }





    /**
     * 删除开库单
     * @param id
     */
    public boolean delete(String id) {
        try {
          return  voucherMapper.delete(id);


        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);

        }
    }
    /**
     * 保存开库单
     * @param voucher
     */
    public int save(Voucher voucher) {
        try {
            return voucherMapper.save(voucher);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);

        }
    }
    /**
     * 更新开库单
     * @param voucher
     */
    public int update(Voucher voucher) {
        try {

            return voucherMapper.update(voucher);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }

    public String generateVC(){

        try{
            return voucherMapper.generateVC();
        }catch(Exception e ){
            e.printStackTrace();
            return "";
        }
    }


}