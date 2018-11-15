package com.hgsoft.zengzhiyingyong.module.rbac.rest;

import com.hgsoft.zengzhiyingyong.module.rbac.domain.Module;
import com.hgsoft.zengzhiyingyong.module.rbac.service.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hegc on 2016/4/7.
 * 功能模块Rest控制器
 */
@RestController
@RequestMapping("/api/v1/module")
public class ModuleRestController {

    private Logger logger = LoggerFactory.getLogger(ModuleRestController.class);

    @Autowired
    private ModuleService moduleService;

    /**
     * 获取树的数据
     * @return
     */
    @RequestMapping(value = "/getAllTree", method = RequestMethod.GET)
    public List<Module> getTree(){
        return moduleService.getAllTreeData();
    }

    /**
     * 通过id获取模块信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Module get(@PathVariable("id")String id) {
        return moduleService.get(id);
    }
    /**
     * 删除
     * @param id
     * @return true-删除成功, false-删除失败
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public boolean delete(@PathVariable("id")String id){
        try {
            moduleService.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 新增数据
     * @param module
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Module save(Module module) {
        try {
            moduleService.save(module);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return module;
    }

    /**
     * 检验名字是否重名
     * @param module 模型
     * @return
     */
    @RequestMapping(value = "/checkName", method = RequestMethod.GET)
    public boolean checkModuleName(Module module){
        try {
            return moduleService.checkModuleName(module);
        } catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
}