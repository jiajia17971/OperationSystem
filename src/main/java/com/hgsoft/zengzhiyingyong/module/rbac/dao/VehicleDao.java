package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.imapper.VehicleMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/7/30.
 * 车辆DAO服务类
 */
@Repository
public class VehicleDao {

    private Logger logger = LoggerFactory.getLogger(VehicleDao.class);

    @Autowired
    private VehicleMapper vehicleMapper;

    public List<Vehicle> findByFaceCardNum(String faceCardNum){
        try {
            List<Vehicle> results = vehicleMapper.findByFaceCardNum(faceCardNum);

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }

    public List<Vehicle> findByCCBCardNum(String faceCardNum){
        try {

            List<Vehicle> results = vehicleMapper.findByCCBCardNum(faceCardNum);

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }

    public int updateCard(Vehicle vehicle){

        return vehicleMapper.updateCard(vehicle);
    }

    public int updateCCBCard(Vehicle vehicle){

        return vehicleMapper.updateCard(vehicle);
    }

    public String findCard(String faceCardNum,String vehiclePlate,String vehicleColor){
        List<Vehicle> vlist = vehicleMapper.findCard(faceCardNum,vehiclePlate,vehicleColor);
        if(vlist.size()==0){
            return null;
        }else{
            return vlist.get(0).getFaceCardNum();
        }

    }

}