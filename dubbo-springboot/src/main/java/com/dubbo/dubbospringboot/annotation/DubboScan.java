package com.dubbo.dubbospringboot.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DubboComponentScanRegistrar.class)
@Documented
public @interface DubboScan {
    /**
     * 扫描特定包
     *
     * @return
     */
    String[] scanPackage() default {};
}
