package com.dubbo.dubbospringboot.dubbo;

import com.dubbo.dubbospringboot.annotation.Service;

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
@Service
public class HelloServiceImpl implements HelloService{

    @Override public String say() {
        return "Hello World";
    }
}
