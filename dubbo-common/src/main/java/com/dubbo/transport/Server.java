package com.dubbo.transport;

import com.dubbo.annotation.SPI;
import com.dubbo.url.SimpleDubboURL;

/**
 * 类说明:
 *
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ ============= ============================
 * VilderLee    2019/7/2      Create this file
 * </pre>
 */
@SPI("netty")
public interface Server {

    void connect(SimpleDubboURL simpleDubboURL) throws Exception;

    void close(SimpleDubboURL simpleDubboURL) throws Exception;
}
