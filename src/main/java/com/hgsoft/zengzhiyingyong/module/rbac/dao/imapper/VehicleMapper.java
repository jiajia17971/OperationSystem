package com.hgsoft.zengzhiyingyong.module.rbac.dao.imapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IDataMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Vehicle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 车辆Mapper接口
 */
@Repository
public interface VehicleMapper extends IDataMapper {

    /**
     * 查询车牌办理

     * @param faceCardNum 通衢卡卡号
     * @return
     */
    List<Vehicle> findByFaceCardNum(@Param("faceCardNum") String faceCardNum);

    /**
     * 查询车牌办理
     * @param faceCardNum 通衢卡卡号
     * @return
     */
    List<Vehicle> findByCCBCardNum(@Param("faceCardNum") String faceCardNum);

    /**
     * 更新客服库办理
     * @param vehicle 通衢卡卡号
     * @return
     */
    int updateCard(@Param("vehicle") Vehicle vehicle);

    /**
     * 更新建行库办理
     * @param vehicle 通衢卡卡号
     * @return
     */
    int updateCCBCard(@Param("vehicle") Vehicle vehicle);


    List<Vehicle> findCard(@Param("faceCardNum")String faceCardNum,@Param("vehiclePlate")String vehiclePlate,@Param("vehicleColor")String vehicleColor);

}