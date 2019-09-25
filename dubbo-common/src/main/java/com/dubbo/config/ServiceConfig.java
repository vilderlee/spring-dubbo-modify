package com.dubbo.config;

import com.dubbo.transport.Server;
import com.dubbo.transport.ServiceLoader;

import java.util.List;

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
public class ServiceConfig<T> {

    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 接口路径
     */
    private Class interfaceClass;
    /**
     * 具体实现类
     */
    private T ref;

    private String id;

    /**
     * 是否暴露服务
     */
    private transient volatile boolean exported;

    /**
     * 采用dubbo原生代码里的配置（这里为什么是多个RegistryConfig 因为其可能有多种配置中心）
     */
    protected List<RegistryConfig> registries;

    private final Server server = (Server) ServiceLoader.load(Server.class).getAdaptive();

    protected synchronized void doExportUrl() {
        if (exported) {
            return;
        }
        //防止服务重复暴露
        exported = true;

        //暴露服务
        export();
    }

    private synchronized void export() {
        //使用netty做通讯方式

        //使用zk做注册中心
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 这里interface是关键字，所以没有全局变量，只有get、set方法
     *
     * @return
     */

    public String getInterface() {
        return interfaceName;
    }

    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
        if (id == null || id.length() == 0) {
            id = interfaceName;
        }
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public void setRegistries(List<RegistryConfig> registries) {
        this.registries = registries;
    }
}
