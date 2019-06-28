package com.study.handler;

import org.springframework.beans.factory.config.BeanDefinition;
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
public class RegistryBeanParser implements BeanDefinitionParser {

    @Override public BeanDefinition parse(Element element, ParserContext parserContext) {


        String id = element.getAttribute("id");



        return null;
    }
}
