package com.hgsoft.zengzhiyingyong.module.rbac.dao;

import com.hgsoft.zengzhiyingyong.common.context.MultipleDataSource;
import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.exception.DataAccessException;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.CardMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.RoleMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.mapper.VoucherMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.umapper.SubAccMapper;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by WQP on 2018/8/30.
 * 车辆DAO
 */
@Repository
public class CardDao {

    private Logger logger = LoggerFactory.getLogger(CardDao.class);
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private SubAccMapper subAccMapper;
    @Autowired
    private VoucherMapper voucherMapper;
    @Autowired
    private RoleMapper roleMapper;

    public Page<Card> query(Page<Card> page, String voucherid,String faceCardNum){
        try {


            int skip = (page.getPageNo() - 1) * page.getPageSize();
            int size = page.getPageSize();
            List<Card> results = cardMapper.query(skip, size,voucherid,faceCardNum);
            Voucher v = voucherMapper.get(voucherid);
            if(v!=null){
                for (Card card:results) {
//                    card.setOptAmount(card.getOptAmount().multiply(new BigDecimal(0.01)));
                    if("1".equals(v.getStatus())){
                        card.setVstate(1);
                    }else if("5".equals(v.getStatus())){
                        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityContextHolder.USER_CONTEXT);
                        User eu = new User();
                        if(user!=null&& user.getRoles()!=null ){//超级管理员
                            List<Role> list = user.getRoles();
                            for(int i = 0;i<list.size();i++){
                                Role r =  (Role)list.get(i);
                                Role re = (Role)roleMapper.get(r.getId());
                                if("checkrole".equals(re.getId())){
                                    card.setVstate(2);
                                    break;
                                }else{
                                    card.setVstate(1);
                                }
                            }


                        }

                    }else{
                        card.setVstate(2);
                    }

                }
            }
            long count = cardMapper.count(voucherid, faceCardNum);
            page.setTotalCount(count);
            page.setResult(results);
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }

    }

    /**
     * 保存卡片
     * @param card
     */
    public int save(Card card) {
        try {

            return cardMapper.save(card);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }

    /**
     * 更新卡片
     * @param card
     */
    public int update(Card card) {
        try {
            MultipleDataSource.setDataSourceKey("dataSource");
            return cardMapper.update(card);

        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new DataAccessException(e.getMessage(), e);

        }
    }

    public List<Card> queryByVoucher(String id){
        return cardMapper.queryByVoucher(id);
    }


    public boolean delete (String id){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        try {
            MultipleDataSource.setDataSourceKey("dataSource");
            return   cardMapper.delete(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 根据id获取卡信息
     * @param id
     * @return
     */
    public Card get(String id){
        try {
           return cardMapper.get(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
    /**
     * 根据id获取卡信息
     * @param faceCardNum
     * @return
     */
    public Card getByCardNo(String faceCardNum){
        try {
           return cardMapper.getByCardNo(faceCardNum);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
    /**
     * 根据卡获取开库单信息
     * @param faceCardNum
     * @return
     */
    public List<Card> getVoucherByCardNo(String faceCardNum){
        try {
           return cardMapper.getVoucherByCardNo(faceCardNum);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<Card> getCardByVoucher(String voucherid){

        return cardMapper.query(0,999,voucherid,null);
    }

    public AccountEntity searchForRecharge(Card card){
        return subAccMapper.searchForRecharge(card);
    }

}