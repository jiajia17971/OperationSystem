package com.hgsoft.zengzhiyingyong.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量持有 Spring ApplicationContext
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    /** 私有构造函数 */
    private ApplicationContextHolder() {}

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.context = applicationContext;
    }

    /**
     * 获取静态变量 context
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 从静态变量 context 中取得 Bean, 自动转型为所赋值对象的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkContext();
        return (T) context.getBean(name);
    }

    /**
     * 从静态变量 context 中取得Bean, 自动转型为所赋值对象的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        checkContext();
        return (T) context.getBeansOfType(clazz);
    }

    /**
     * 清除 context 静态变量
     */
    public static void cleanContext() {
        context = null;
    }

    private static void checkContext() {
        if (context == null) {
            throw new IllegalStateException("上下文未注入, 请在 spring 容器中定义 ApplicationContextHolder");
        }
    }
}
