package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.UploadEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 附件文件Mapper接口
 */
@Repository
public interface UploadMapper extends IBaseMapper {


    boolean save(@Param("upload") UploadEntity upload);

    boolean update(@Param("voucherid") int voucherid,@Param("ids") String[] ids );

    List<UploadEntity> find(@Param("id") int id);

    UploadEntity getAttachment(@Param("path") String path);

}