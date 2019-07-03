package com.study.transport;

import com.study.url.SimpleDubboURL;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
public class SocketTransportServer implements Server {

    @Override public void connect(SimpleDubboURL simpleDubboURL) throws Exception {
        int port = simpleDubboURL.getPort();
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService executor = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        executor.execute(new Task(serverSocket.accept()));
    }

    @Override public void close(SimpleDubboURL simpleDubboURL) throws Exception {

    }

    private class Task implements Runnable {

        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override public void run() {
            try (InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                Object o = objectInputStream.readObject();
                if (o instanceof SimpleDubboURL){
                    SimpleDubboURL simpleDubboURL = (SimpleDubboURL) o;
                    Map map = (Map) simpleDubboURL.getParameters().get("dubbo");
                    map.get("interface");
                    map.get("method");
                    map.get("params");
                }else {
                    throw new Exception("data is not SimpleDubboURL");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
