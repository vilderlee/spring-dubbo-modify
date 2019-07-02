package com.study;

import com.study.transport.Server;
import com.study.transport.ServiceLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        ServiceLoader<Server> serviceLoaderTest = ServiceLoader.load(Server.class);
        Server server = serviceLoaderTest.getExtension("netty");
        server.connect();

    }
}
