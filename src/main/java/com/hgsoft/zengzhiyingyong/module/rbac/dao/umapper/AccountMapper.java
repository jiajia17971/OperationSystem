package com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.common.mapper.IDataMapper;
import com.hgsoft.zengzhiyingyong.common.mapper.IRechargeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.RechargeUpload;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by WQP on 2018/8/2.
 * 卡账调整Mapper接口
 */
@Repository
public interface AccountMapper extends IRechargeMapper {

    /**
     * 创建虚拟订单
     * @param card
     * @return
     */
    int createOrder(@Param("card") Card card);

    /**
     * 修改虚拟账户余额
     * @param card
     * @return
     */
    int updateAccount(@Param("card")Card card);

    /**
     *插入log日志
     * @param card
     * @return
     */
    int insertLog(@Param("card")Card card);
    /**
     *插入log日志
     * @param card
     * @return
     */
    int insertRecharge(@Param("card")Card card,@Param("voucher") Voucher voucher);

    int insertRechargeLog(@Param("card")Card card);

    int inserUploadRecord(@Param("upload")RechargeUpload upload);

}