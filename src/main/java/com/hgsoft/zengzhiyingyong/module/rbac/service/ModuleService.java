package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.hgsoft.zengzhiyingyong.module.rbac.dao.ModuleDao;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Module;
import com.hgsoft.zengzhiyingyong.util.Identities;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hegc on 2016/4/4.
 * 模块Service
 */
@Service
@Transactional
public class ModuleService {

    private Logger logger = LoggerFactory.getLogger(ModuleService.class);

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 获取所有生效的节点
     * @return
     */
    @Transactional(readOnly = true)
    public List<Module> getTreeData() {
        return moduleDao.getTreeData();
    }

    /**
     * 获取所有的节点
     * @return
     */
    @Transactional(readOnly = true)
    public List<Module> getAllTreeData(){
        return moduleDao.getAllTreeData();
    }

    @Transactional(readOnly = true)
    public Module get(String id) {
        return moduleDao.get(id);
    }
    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        moduleDao.delete(id);
        moduleDao.deleteModuleRole(id);
    }

    /**
     * 保存
     * @param module
     */
    public void save(Module module){
        //如果父级节点为空, 则层次为1; 否则为2
        if(StringUtils.isBlank(module.getParentId())) {
            module.setLevels(1);
        } else {
            module.setLevels(2);
        }
        if(StringUtils.isNotBlank(module.getId())) {
            moduleDao.update(module);
        } else {
            module.setId(Identities.uuid());
            moduleDao.save(module);
        }
    }


    /**
     * 校验是否重名
     * @param module
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkModuleName(Module module){
        return moduleDao.countModuleName(module) == 0;
    }

    /**
     * 根据用户id获取菜单
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Module> getMenus(String userId){
        return moduleDao.getMenus(userId);
    }

    /**
     * 获取url可以被哪些角色访问
     * @return
     */
    @Transactional(readOnly = true)
    public List<Module> getUrlRoles(){
        return moduleDao.getUrlRoles();
    }
}