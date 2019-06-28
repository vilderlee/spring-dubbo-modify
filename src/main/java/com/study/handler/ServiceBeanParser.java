package com.study.handler;

import com.study.config.ServiceConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
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
public class ServiceBeanParser implements BeanDefinitionParser {


    @Override public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();

        String id = element.getAttribute("id");
        String interfaceClass = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        beanDefinition.setBeanClass(ServiceConfig.class);
        beanDefinition.setLazyInit(false);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("interface", interfaceClass);
        beanDefinition.getPropertyValues().addPropertyValue("ref", new RuntimeBeanReference(ref));

        //beanName
        parserContext.getRegistry().registerBeanDefinition(id,beanDefinition);

        return beanDefinition;
    }
}
