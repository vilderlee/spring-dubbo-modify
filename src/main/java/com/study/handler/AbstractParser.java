package com.study.handler;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
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
public abstract class AbstractParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinition beanDefinition = new RootBeanDefinition();

        String id = element.getAttribute("id");
        if (StringUtils.isEmpty(id)){

        }

        doParser(element, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(id,beanDefinition);
        return beanDefinition;
    }

    protected abstract void doParser(Element element, BeanDefinition beanDefinition);
}
