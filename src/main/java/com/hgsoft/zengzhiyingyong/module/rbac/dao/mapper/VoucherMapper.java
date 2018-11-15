package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 用户Mapper接口
 */
@Repository
public interface VoucherMapper extends IBaseMapper {

    /**
     * 分页查询用户信息
     * @param user 用户
     * @param skip 第几条
     * @param size 每页页码
     * @return
     */
    List<Voucher> query(@Param("user") User user, @Param("skip") int skip, @Param("size") int size,@Param("voucher") Voucher voucher,@Param("start")String start,@Param("end") String end);

    /**
     * 统计总数
     * @param user
     * @return
     */
    long count(@Param("user") User user,@Param("voucher")Voucher voucher,@Param("start") String start,@Param("end") String end);

    /**
     * 通过id获取开库单信息
     * @param id
     * @return
     */
    Voucher get(@Param("id") String id);
    /**
     * 通过id获取开库单信息
     * @param voucherid
     * @return
     */
    Voucher getVoucherByCode(@Param("voucherid") String voucherid);
    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    Voucher getInfo(@Param("id") String id);

    /**
     * 通过id删除(注销)开库单
     * @param id
     * @return
     */
    boolean delete(@Param("id") String id);
    /**
     * save开库单
     * @param voucher
     * @return
     */
    int save(@Param("voucher") Voucher voucher);
    /**
     * save开库单
     * @param voucher
     * @return
     */
    int update(@Param("voucher") Voucher voucher);

    String generateVC();

}