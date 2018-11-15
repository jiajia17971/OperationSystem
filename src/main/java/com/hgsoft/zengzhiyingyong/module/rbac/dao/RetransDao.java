package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.module.rbac.dao.emapper.RetransMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ReloadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by WQP on 2018/8/30.
 * 数据重发执行DAO
 */
@Repository
public class RetransDao {

    @Autowired
    RetransMapper retransMapper;

    public boolean delete_sum(ReloadEntity load){
        return retransMapper.delete_sum(load);
    }

    public boolean delete_comfirm(ReloadEntity load){
        return  retransMapper.delete_comfirm(load);
    }

    public boolean update_Recv(ReloadEntity load){
        return retransMapper.update_Recv(load);
    }

    public ReloadEntity searchSummary(String batchno,String orig){
        return  retransMapper.searchSummary(batchno,orig);
    }
}