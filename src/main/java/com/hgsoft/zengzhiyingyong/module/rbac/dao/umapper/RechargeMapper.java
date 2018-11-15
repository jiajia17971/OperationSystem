package com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IDataMapper;
import com.hgsoft.zengzhiyingyong.common.mapper.IRechargeMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.RechargeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface RechargeMapper extends IRechargeMapper {

     List<RechargeEntity>  query(@Param("skip") int skip, @Param("size") int size, @Param("faceCardNum") String faceCardNum, @Param("startTime") String startTime, @Param("endTime") String endTime);

     long count(@Param("faceCardNum")String faceCardNum,@Param("startTime")String startTime,@Param("endTime")String endTime);

     boolean updateRechargeList(@Param("card")Card card);


}
