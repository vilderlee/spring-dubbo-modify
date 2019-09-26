package com.dubbo.dubbospringboot.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

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
public class DubboComponentScanRegistrar implements ImportBeanDefinitionRegistrar {
    @Override public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {

        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(DubboScan.class.getName()));

        assert annotationAttributes != null;
        String[] scanPackages = annotationAttributes.getStringArray("scanPackage");

        ClassPathBeanDefinitionScanner scanner = new DubboClassPathBeanDefinitionScanner(registry, false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));

        Stream.of(scanPackages).forEach(toScanPackage -> {

                    registerBean(registry, toScanPackage, scanner);

                }

        );

    }

    private void registerBean(BeanDefinitionRegistry registry, String toScanPackage,
            ClassPathBeanDefinitionScanner scanner) {
        scanner.scan(toScanPackage);


        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(toScanPackage);
        Set<BeanDefinitionHolder> beanDefinitionHolders = new LinkedHashSet<>(candidateComponents.size());
        candidateComponents.forEach(beanDefinition -> {
            String beanName = generateBeanName(beanDefinition, registry);

            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition, beanName);
            beanDefinitionHolders.add(beanDefinitionHolder);
            registry.registerBeanDefinition(beanName, beanDefinitionHolder.getBeanDefinition());
        });
        System.out.println();

    }

    private String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        DubboBeanNameGenerator dubboBeanNameGenerator = new DubboBeanNameGenerator(, );
        return dubboBeanNameGenerator.build();
    }
}
