package com.study.transport;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 类说明:
 *
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ ============= ============================
 * VilderLee    2019/7/4      Create this file
 * </pre>
 */

@Data
@ToString
public class RpcInvocation implements Serializable {

    private static final long serialVersionUID = 2586514430172942791L;
    /**
     * 调用接口
     */
    private String interfaceName;
    /**
     * 调用接口方法名称
     */
    private String methodName;
    /**
     * 调用接口参数
     */
    private Object[] args;
}
