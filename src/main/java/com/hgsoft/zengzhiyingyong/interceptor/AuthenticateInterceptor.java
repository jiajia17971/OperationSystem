package com.hgsoft.zengzhiyingyong.interceptor;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 身份认证拦截器
 * 1.spring-mvc.xml文件中免验证的url
 */
public class AuthenticateInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(AuthenticateInterceptor.class);

    /**
     * 拦截器排除的请求集合
     */
    private String[] excludes;

    public AuthenticateInterceptor(String[] excludes) {
        this.excludes = excludes;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;

        String ctx = request.getContextPath();
        String uri = request.getRequestURI();

        if (excludes != null) {
            for (String exclude : excludes) {
                // 如果包含当前请求, 则不拦截
                if (uri.startsWith(ctx + exclude)) {
                    result = true;
                    break;
                }
            }
            if (result) {
                logger.debug("身份认证拦截器排除的请求: {}", uri);
                return result;
            }
        }
        User user = SecurityContextHolder.getUser();
        if(!ObjectUtils.notEqual(null, user)) {
            response.sendError(403);
        }
        return result;
    }
}