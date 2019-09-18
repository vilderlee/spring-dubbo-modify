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

    private Class clz;

    public AbstractParser(Class clz) {
        this.clz = clz;
    }

    @Override public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();

        String id = element.getAttribute("id");
        if (StringUtils.isEmpty(id)) {
            String name = element.getAttribute("name");

            if (StringUtils.isEmpty(name)) {
                //可能是service类，获取接口
                name = element.getAttribute("interface");
                if (StringUtils.isEmpty(name)) {
                    name = clz.getName();
                }
            }
            id = name;
        }
        beanDefinition.setBeanClass(clz);
        beanDefinition.setLazyInit(false);

        doParser(element, beanDefinition);
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        return beanDefinition;
    }

    protected abstract void doParser(Element element, BeanDefinition beanDefinition);
}
