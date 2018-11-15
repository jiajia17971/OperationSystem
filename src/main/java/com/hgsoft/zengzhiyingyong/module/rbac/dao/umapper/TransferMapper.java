package com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IRechargeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.RechargeUpload;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.TransferEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 圈存数据Mapper接口
 */
@Repository
public interface TransferMapper extends IRechargeMapper {

    List<TransferEntity> query(@Param("faceCardNum") String faceCardNum,@Param("startTime") String startTime,@Param("endTime") String endTime);


}