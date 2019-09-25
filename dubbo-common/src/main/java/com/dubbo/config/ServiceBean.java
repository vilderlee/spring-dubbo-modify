package com.dubbo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override public void afterPropertiesSet() throws Exception {
        String interfaceName = getInterface();

        Class interfaceClass = Class.forName(interfaceName);
        super.setInterface(interfaceName);
        super.setInterfaceName(interfaceName);
        super.setInterfaceClass(interfaceClass);

        //关于注册中心配置
        if (getRegistries().size() == 0) {
            //通过内置方法获取注册中心的bean
            Map<String, RegistryConfig> registryConfigMap = BeanFactoryUtils
                    .beansOfTypeIncludingAncestors(SPRING_CONTEXT, RegistryConfig.class, false, false);
            List<RegistryConfig> registryConfigs = new ArrayList<>();
            if (!CollectionUtils.isEmpty(registryConfigMap)) {
                registryConfigMap.forEach((k, v) -> registryConfigs.add(v));
                setRegistries(registryConfigs);
            }
        }

        //暴露服务
        doExportUrl();
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SPRING_CONTEXT = applicationContext;
    }

    @Override public void setBeanName(String name) {
        this.beanName = name;
    }
}
