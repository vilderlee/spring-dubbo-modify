package com.study.config;

import com.study.config.ServiceConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类说明:
 *
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ ============= ============================
 * VilderLee    2019/6/28      Create this file
 * </pre>
 */
public class ServiceBean extends ServiceConfig implements BeanNameAware, ApplicationContextAware, InitializingBean {

    private static ApplicationContext SPRING_CONTEXT;

    private String beanName;

    public static ApplicationContext getSpringContext() {
        return SPRING_CONTEXT;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String interfaceName = getInterface();

        Class interfaceClass = Class.forName(interfaceName);
        super.setInterfaceName(interfaceName);
        super.setInterfaceClass(interfaceClass);
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SPRING_CONTEXT = applicationContext;
    }

    @Override public void setBeanName(String name) {
        this.beanName = name;
    }
}
