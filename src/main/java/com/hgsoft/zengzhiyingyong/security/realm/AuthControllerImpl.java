package com.hgsoft.zengzhiyingyong.security.realm;


import com.hgsoft.zengzhiyingyong.common.context.ApplicationContextHolder;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Module;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.service.ModuleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 *　加载资源文件
 */
public class AuthControllerImpl implements IAuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthControllerImpl.class);

    private String filterChainDefinitions;

    public static final String ROLE_OR_STRING="roleOrFilter[\"{0}\"]";

    @Autowired
    private ModuleService moduleService;

    public synchronized String loadFilterChainDefinitions() {
        logger.info("权限加载");
        Ini.Section section = loadFilterChain();
        StringBuffer stringBuffer = new StringBuffer("");
        for (Map.Entry<String, String> entry : section.entrySet()) {
            stringBuffer.append(entry.getKey() + " = " + entry.getValue() + "\r\n");
        }
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }

    public synchronized void reCreateFilterChains() {
        logger.info("权限重载");
        AbstractShiroFilter shiroFilter = null;
        try{
			shiroFilter = (AbstractShiroFilter) ApplicationContextHolder.getBean("shiroFilter");
        } catch(Exception e) {
            logger.error(e.getMessage());
        }

		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
				.getFilterChainManager();

        //清空老的权限控制
        manager.getFilterChains().clear();
        Ini.Section section = loadFilterChain();
        Map<String, String> filterListMap = section;
        for (Map.Entry<String, String> entry : filterListMap.entrySet()) {
            manager.createChain(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 加载权限资源
     * @return
     */
    private Ini.Section loadFilterChain() {
        Ini ini = new Ini();
        //加载默认的url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        List<Module> moduleList = moduleService.getUrlRoles();
        for(Module module : moduleList) {
            StringBuilder builder = new StringBuilder();
            List<Role> roles = module.getRoles();
            if(CollectionUtils.isNotEmpty(roles)) {
                for (int i = 0; i < roles.size(); i++) {
                    if(i != roles.size() - 1){
                        builder.append(roles.get(i).getId()).append(",");
                    } else {
                        builder.append(roles.get(i).getId());
                    }
                }
            }
            if(StringUtils.isNotBlank(builder.toString())) {
                section.put(module.getUrl() + "*", MessageFormat.format(ROLE_OR_STRING, builder.toString()));
            } else {
                section.put(module.getUrl() + "*", MessageFormat.format(ROLE_OR_STRING, "SUPER"));
            }
        }
        section.put("/**", "authc");
        return section;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }
}
