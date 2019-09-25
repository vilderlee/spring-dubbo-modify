package com.dubbo.dubbospringboot.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 类说明:
 *
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ ============= ============================
 * VilderLee    2019/9/25      Create this file
 * </pre>
 */
public class DubboClassPathBeanDefinitionScanner implements EnvironmentAware, BeanClassLoaderAware, BeanFactoryAware {

    private final Set<String> packages;

    private Environment environment;

    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    public DubboClassPathBeanDefinitionScanner(String...packages){
        this(Arrays.asList(packages));
    }

    public DubboClassPathBeanDefinitionScanner(Collection<String> packages) {
        this(new LinkedHashSet<String>(packages));
    }

    public DubboClassPathBeanDefinitionScanner(Set<String> packages) {
        this.packages = packages;
    }

    public void scan(){

    }

    @Override public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
