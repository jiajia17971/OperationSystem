package com.hgsoft.zengzhiyingyong.security.realm;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Role;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UserService;
import com.hgsoft.zengzhiyingyong.support.UserStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 认证与授权服务
 */
public class PermissionsRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(PermissionsRealm.class);

    @Autowired
    private UserService userService;


	public PermissionsRealm(){
		setName("PermissionsRealm");
	}

    /**
     * 授权
     * @param principals
     * @return
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info(principals.getRealmNames() + "授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityContextHolder.USER_CONTEXT);
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            info.addRole(role.getId());
        }
		return info;
	}


    /**
     * 认证
     * @param authToken
     * @return
     * @throws AuthenticationException
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
//        LoginToken token = (LoginToken) authToken;
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String loginName = StringUtils.trim(token.getUsername());
        String password = String.valueOf(token.getPassword());

        logger.info("用户{}尝试登陆", loginName);

        if (StringUtils.isBlank(loginName) || StringUtils.isEmpty(password)) {
            throw new AuthenticationException("用户名或密码为空，请重新输入！");
        }

        // 设置加密后的密码
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(password);
        user = userService.login(loginName, password);

        if (user != null) {
            //1 表示锁定状态
            if (user.getStatus() == UserStatus.LOCK.id()) {
                throw new AuthenticationException("您已被禁止登录，请联系管理员");
            }
            List<Role> roles = user.getRoles();
            if(CollectionUtils.isEmpty(roles)) {
                throw new AuthenticationException("当前用户没有分配角色，请分配角色后再登录");
            }
            userService.updateLoginTime(loginName);
            SecurityUtils.getSubject().getSession().setAttribute(SecurityContextHolder.USER_CONTEXT, user);
			return new SimpleAuthenticationInfo(token.getPrincipal(),
					token.getCredentials(), token.getUsername());
		}
        throw new AuthenticationException("用户名或密码错误");
    }

}
