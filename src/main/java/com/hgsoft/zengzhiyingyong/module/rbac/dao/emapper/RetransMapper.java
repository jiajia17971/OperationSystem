package com.hgsoft.zengzhiyingyong.module.rbac.dao.emapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IDataMapper;
import com.hgsoft.zengzhiyingyong.common.mapper.IReloadMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.RechargeEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ReloadEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetransMapper extends IReloadMapper {

     boolean delete_sum(@Param("load") ReloadEntity load);

     boolean delete_comfirm(@Param("load") ReloadEntity load);

     boolean update_Recv(@Param("load") ReloadEntity load);

     ReloadEntity searchSummary(@Param("batchno")String batchno,@Param("orgid")String orgid);

}
