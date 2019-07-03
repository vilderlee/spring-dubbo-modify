package com.study.transport;

import com.study.annotation.SPI;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ServiceLoader<S> {

    private final Class<S> clz;
    private final ClassLoader classLoader;

    /**
     * 用于存储文件中的key、value值
     */
    private final Map<String, Holder> CONTAINER = new ConcurrentHashMap<>();
    private final Map<String, Class> CONTAINER_CLASS = new ConcurrentHashMap<>();
    private static final Map<Class, ServiceLoader> CONTAINER_SERVICELOADER = new ConcurrentHashMap<>();
    /**
     * 加载该目录下的插件
     */
    private static final String PREFIX = "META-INF/services/";

    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

    /**
     * @ SPI注解的值
     */
    private String cachedDefaultName;

    private ServiceLoader(Class<S> clz, ClassLoader classLoader) {
        this.clz = clz;
        if (null == classLoader) {
            this.classLoader = ClassLoader.getSystemClassLoader();
        } else {
            this.classLoader = classLoader;
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

    public S getExtension(String name) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new Exception("name is null");
        }

        Holder holder = CONTAINER.get(name);
        if (holder == null) {
            CONTAINER.putIfAbsent(name, new Holder<>());
            holder = CONTAINER.get(name);
        }
        Object instance = holder.getValue();
        //双重锁机制
        if (instance == null) {
            synchronized (this) {
                instance = holder.getValue();
                if (instance == null) {
                    instance = createInstance(name);
                }
            }
        }
        return (S) instance;

    }

    //创建实例
    private Object createInstance(String name) {
        Class clz = CONTAINER_CLASS.get(name);
        if (null == clz) {
            createContainer();
        }
            clz = CONTAINER_CLASS.get(name);
        try {
            return clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //创建容器
    private void createContainer() {
        SPI defaultAnnotation = clz.getAnnotation(SPI.class);
        if (defaultAnnotation != null) {
            String value = defaultAnnotation.value();
            if ((value = value.trim()).length() > 0) {
                String[] names = NAME_SEPARATOR.split(value);
                if (names.length > 1) {
                    throw new IllegalStateException(
                            "more than 1 default extension name on extension " + clz.getName() + ": " + Arrays
                                    .toString(names));
                }
                if (names.length == 1) {
                    cachedDefaultName = names[0];
                }
            }
        }
        //解析文件
        parse();
    }


    //找到文件，依次解析
    private void parse() {
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

    //解析URL的数据
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
                CONTAINER_CLASS.put(args[0], initClz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Holder<T> {
        private volatile T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    /**
     * dubbo原生是通过字节码技术生成一个名称为<接口名称$Adaptive>的实现类，这个类会去判断实际的URL参数的对应的参数值，从而获取对应的插件
     * <p>
     * 我这里并不打算使用这种方式去做，因为我做不来（简易版就不要那么高要求了！）
     * <p>
     * 直接使用SPI注解的值作为参数值
     *
     * @return
     */
    public S getAdaptive() throws Exception {
        createContainer();
        return getExtension(cachedDefaultName);
    }

    public static void main(String[] args) throws Exception {
        Server server = (Server) ServiceLoader.load(Server.class).getAdaptive();
    }
}