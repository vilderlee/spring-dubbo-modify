package com.study.handler;

import com.study.config.RegistryConfig;
import com.study.config.ServiceConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

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
public class UserNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new RegistryBeanParser(RegistryConfig.class));
        registerBeanDefinitionParser("service", new ServiceBeanParser(ServiceConfig.class));
    }
}
