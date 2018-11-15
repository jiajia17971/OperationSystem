package com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper;

import com.hgsoft.zengzhiyingyong.common.mapper.IBaseMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Business;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Card;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WQP on 2018/8/2.
 * 卡账调整Mapper接口
 */
@Repository
public interface CardMapper extends IBaseMapper {

    /**
     * 分页查询卡项列表
     * @param skip 第几条
     * @param size 每页页码
     * @return
     */
    List<Card> query(@Param("skip") int skip, @Param("size") int size, @Param("voucherid") String voucherid, @Param("faceCardNum")String faceCardNum);

    /**
     * 统计总数
     * @return
     */
    long count(@Param("voucherid") String voucherid, @Param("faceCardNum")String faceCardNum);

    /**
     * save卡片
     * @param card
     * @return
     */
    int save(@Param("card") Card card);
    /**
     * save卡片
     * @param card
     * @return
     */
    int update(@Param("card") Card card);
    /**
     * delete卡片
     * @param id
     * @return
     */
    boolean delete(@Param("id") String id);

     Card get(@Param("id")String id);

     Card getByCardNo(@Param("faceCardNum")String faceCardNum);

     List<Card> getVoucherByCardNo(@Param("faceCardNum") String faceCardNum);

     List<Card> queryByVoucher(@Param("id") String id);

}