package com.study.config;

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

    public String getInterface(){
        return interfaceName;
    }


    public void setInterface(String interfaceName){
        this.interfaceName = interfaceName;
        if (id == null || id.length() == 0) {
            id = interfaceName;
        }
    }
}
