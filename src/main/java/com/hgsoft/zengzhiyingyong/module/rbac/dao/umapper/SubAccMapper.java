package com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.common.mapper.IDataMapper;
import com.hgsoft.zengzhiyingyong.common.mapper.IRechargeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.AccountEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 虚拟账户Mapper接口
 */
@Repository
public interface SubAccMapper extends IRechargeMapper {

     AccountEntity searchForRecharge(@Param("card") Card card);

}