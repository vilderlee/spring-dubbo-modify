package com.dubbo.dubbospringboot.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.StringUtils;

/**
 * 类说明:
 *
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ ============= ============================
 * VilderLee    2019/9/26      Create this file
 * </pre>
 */
public class DubboBeanNameGenerator  {

    private Service service;

    private Class interfaces;

    public DubboBeanNameGenerator(Service service, Class interfaces) {
        this.service = service;
        this.interfaces = interfaces;
    }


    public String build(){
        if (!StringUtils.isEmpty(service.name())){
            return service.name();
        }

        return "";
    }
}
