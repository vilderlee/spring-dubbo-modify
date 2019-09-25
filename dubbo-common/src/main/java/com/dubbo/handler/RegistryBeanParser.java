package com.dubbo.handler;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

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
public class RegistryBeanParser extends AbstractParser {

    public RegistryBeanParser(Class clz) {
        super(clz);
    }

    @Override protected void doParser(Element element, BeanDefinition beanDefinition) {
        String host = element.getAttribute("host");
        if (!StringUtils.isEmpty(host)) {
            beanDefinition.getPropertyValues().addPropertyValue("host", host);
        }else {
            throw new RuntimeException("Registry host is Empty !");
        }
    }
}
