package com.dubbo.handler;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
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
public class ServiceBeanParser extends AbstractParser {

    public ServiceBeanParser(Class clz) {
        super(clz);
    }

    @Override protected void doParser(Element element, BeanDefinition beanDefinition) {
        String interfaceClass = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        beanDefinition.getPropertyValues().addPropertyValue("interface", interfaceClass);
        //ref封装成RuntimeBeanReference原因是spring容器在解析依赖注入的属性时，
        //如果是RuntimeBeanReference会去判断spring是否存在ref的这个bean
        beanDefinition.getPropertyValues().addPropertyValue("ref", new RuntimeBeanReference(ref));
    }
}
