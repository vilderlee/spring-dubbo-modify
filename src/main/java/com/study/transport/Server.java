package com.study.transport;

import com.study.annotation.SPI;

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

    void connect() throws Exception;

    void close() throws Exception;
}
