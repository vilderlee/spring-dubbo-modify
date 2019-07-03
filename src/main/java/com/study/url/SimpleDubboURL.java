package com.study.url;

import java.io.Serializable;
import java.util.Map;

/**
 * 类说明:
 *  registry://192.168.1.7:9090/com.alibaba.service1?param1=value1&amp;param2=value2
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ ============= ============================
 * VilderLee    2019/7/2      Create this file
 * </pre>
 */
public class SimpleDubboURL implements Serializable {

    private static final long serialVersionUID = 8542785340880264437L;
    private final String protocol;

    private final String username;

    private final String password;

    private final String host;

    private final int port;

    private final String path;

    private final Map<String, Object> parameters;

    public SimpleDubboURL(String protocol, String host, int port) {
        this(protocol, null, null, host, port, null, (Map<String, Object>) null);
    }

    public SimpleDubboURL(String protocol, String host, int port, Map<String, Object> parameters) {
        this(protocol, null, null, host, port, null, parameters);
    }

    public SimpleDubboURL(String protocol, Map<String, Object> parameters) {
        this(protocol, null, null, null, 0, null, parameters);
    }

    public SimpleDubboURL(String protocol, String username, String password, String host, int port, String path,
            Map<String, Object> parameters) {
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
