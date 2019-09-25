package com.dubbo;

import com.dubbo.transport.Server;
import com.dubbo.transport.ServiceLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        ServiceLoader<Server> serviceLoaderTest = ServiceLoader.load(Server.class);
        Server server = serviceLoaderTest.getExtension("netty");
    }
}
