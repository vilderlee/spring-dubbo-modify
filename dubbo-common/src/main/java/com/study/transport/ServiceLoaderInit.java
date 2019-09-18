package com.study.transport;//package com.study.transport;
//
//import com.study.annotation.SPI;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.Enumeration;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 类说明:
// *
// * <pre>
// * Modify Information:
// * Author        Date          Description
// * ============ ============= ============================
// * VilderLee    2019/7/2      Create this file
// * </pre>
// */
//public class ServiceLoaderInit<S> {
//
//    private final Class<S> clz;
//    private final ClassLoader classLoader;
//
//    private static final Map<String, Object> CONTAINER = new ConcurrentHashMap<>();
//    private static final Map<Class, ServiceLoaderInit> CONTAINER_SERVICELOADER = new ConcurrentHashMap<>();
//    private static final String PREFIX = "META-INF/services/";
//
//    private ServiceLoaderInit(Class<S> clz, ClassLoader classLoader) {
//        this.clz = clz;
//        if (null == classLoader) {
//            this.classLoader = ClassLoader.getSystemClassLoader();
//        } else {
//            this.classLoader = classLoader;
//        }
//        try {
//            String fileName = PREFIX + clz.getName();
//            Enumeration<URL> urlEnumerations = classLoader.getResources(fileName);
//            while (urlEnumerations.hasMoreElements()) {
//                URL url = urlEnumerations.nextElement();
//                doParse(url);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private <S> void doParse(URL url) {
//        try (InputStream inputStream = url.openStream();
//                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
//
//            byte[] chars = new byte[1024];
//            int i;
//            while ((i = bufferedInputStream.read(chars)) != -1) {
//                bufferedInputStream.read(chars, 0, i);
//            }
//            //解析的地址
//            String string = new String(chars);
//            String [] lines = string.split("\r\n");
//            for (int j = 0; j < lines.length; j++) {
//                String[] args = lines[j].split("=");
//                Class initClz = Class.forName(args[1].trim(),true, classLoader);
//                CONTAINER.put(args[0], initClz.newInstance());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static <S> ServiceLoaderInit load(Class<S> clz) throws Exception {
//        //必须是接口
//        if (!clz.isInterface()){
//            throw new Exception("extension is not interface");
//        }
//        //必须由SPI注解注释
//        if (!clz.isAnnotationPresent(SPI.class)){
//            throw new Exception("extension is not extension without @" + SPI.class.getName());
//        }
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        return load(clz, classLoader);
//    }
//
//    private static <S> ServiceLoaderInit load(Class<S> clz, ClassLoader classLoader) throws Exception {
//        ServiceLoaderInit serviceLoaderInit;
//        if (CONTAINER_SERVICELOADER.containsKey(clz)) {
//            serviceLoaderInit = CONTAINER_SERVICELOADER.get(clz);
//        } else {
//            serviceLoaderInit = new ServiceLoaderInit<>(clz, classLoader);
//            CONTAINER_SERVICELOADER.put(clz, serviceLoaderInit);
//        }
//        return serviceLoaderInit;
//    }
//
//    public S getExtension(String name) {
//        return (S) CONTAINER.get(name);
//    }
//
//
//
//
//}
