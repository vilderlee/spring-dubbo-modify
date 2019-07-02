package com.study.transport;

import com.study.annotation.SPI;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceLoader<S> {

    private final Class<S> clz;
    private final ClassLoader classLoader;

    private static final Map<String, Object> CONTAINER = new ConcurrentHashMap<>();
    private static final Map<Class, ServiceLoader> CONTAINER_SERVICELOADER = new ConcurrentHashMap<>();
    private static final String PREFIX = "META-INF/services/";

    private ServiceLoader(Class<S> clz, ClassLoader classLoader) {
        this.clz = clz;
        if (null == classLoader) {
            this.classLoader = ClassLoader.getSystemClassLoader();
        } else {
            this.classLoader = classLoader;
        }
    }

    private <S> void doParse(URL url) {
        try (InputStream inputStream = url.openStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

            byte[] chars = new byte[1024];
            int i;
            while ((i = bufferedInputStream.read(chars)) != -1) {
                bufferedInputStream.read(chars, 0, i);
            }
            //解析的地址
            String string = new String(chars);
            String[] lines = string.split("\r\n");
            for (int j = 0; j < lines.length; j++) {
                String[] args = lines[j].split("=");
                Class initClz = Class.forName(args[1].trim(), true, classLoader);
                CONTAINER.put(args[0], initClz.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <S> ServiceLoader load(Class<S> clz) throws Exception {
        //必须是接口
        if (!clz.isInterface()) {
            throw new Exception("extension is not interface");
        }
        //必须由SPI注解注释
        if (!clz.isAnnotationPresent(SPI.class)) {
            throw new Exception("extension is not extension without @" + SPI.class.getName());
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return load(clz, classLoader);
    }

    private static <S> ServiceLoader load(Class<S> clz, ClassLoader classLoader) throws Exception {
        ServiceLoader serviceLoader;
        if (CONTAINER_SERVICELOADER.containsKey(clz)) {
            serviceLoader = CONTAINER_SERVICELOADER.get(clz);
        } else {
            serviceLoader = new ServiceLoader<>(clz, classLoader);
            CONTAINER_SERVICELOADER.put(clz, serviceLoader);
        }
        return serviceLoader;
    }

    public S getExtension(String name) {

        Object instance = CONTAINER.get(name);

        if (instance == null) {
            CONTAINER.putIfAbsent(name, instance);
            instance = CONTAINER.get(name);
        }
        synchronized (this) {
            if (instance == null) {
                try {
                    String fileName = PREFIX + clz.getName();
                    Enumeration<URL> urlEnumerations = classLoader.getResources(fileName);
                    while (urlEnumerations.hasMoreElements()) {
                        URL url = urlEnumerations.nextElement();
                        doParse(url);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return (S) instance;

    }

}