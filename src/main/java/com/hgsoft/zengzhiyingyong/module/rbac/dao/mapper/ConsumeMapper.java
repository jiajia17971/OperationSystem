package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IDataMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ConsumeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

     @Repository
     public interface ConsumeMapper extends IBaseMapper {


          List<ConsumeEntity>  query(@Param("skip") int skip, @Param("size") int size, @Param("faceCardNum") String faceCardNum, @Param("startTime") String startTime, @Param("endTime") String endTime);

          List<ConsumeEntity>  query2(@Param("skip") int skip, @Param("size") int size, @Param("faceCardNum") String faceCardNum,@Param("ids")List<Integer> ids, @Param("startTime") String startTime, @Param("endTime") String endTime);

          long count(@Param("faceCardNum")String faceCardNum,@Param("startTime")String startTime,@Param("endTime")String endTime);

          long count2(@Param("ids")List<Integer> ids,@Param("faceCardNum")String faceCardNum,@Param("startTime")String startTime,@Param("endTime")String endTime);

          ConsumeEntity get(@Param("inlistno")String inlistno);

          int updateComsumeList(@Param("card")Card card);

          int updateConsumeHead(@Param("card")Card card);

          int updateConsumeTail(@Param("card")Card card);

          int insertLoseDataList(@Param("card")Card card);

          ConsumeEntity getByCondition( @Param("faceCardNum") String faceCardNum,@Param("tradeTime")String tradeTime,@Param("mode")String mode);

          List<ConsumeEntity> getByTransNo( @Param("faceCardNum") String faceCardNum,@Param("start")int start,@Param("end")int end,@Param("startTime")String startTime,@Param("endTime")String endTime);


     }


