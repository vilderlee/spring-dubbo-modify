package com.dubbo.transport;

import com.dubbo.url.SimpleDubboURL;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    private ServerSocket serverSocket;

    private ThreadFactory threadFactory = (Runnable r) -> {
        AtomicInteger poolNumber = new AtomicInteger(1);
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        String namePrefix = "Socket" + poolNumber.getAndIncrement() + "-Dubbo-Thread";
        AtomicInteger threadNumber = new AtomicInteger(1);
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    };

    @Override public void connect(SimpleDubboURL simpleDubboURL) throws Exception {
        int port = simpleDubboURL.getPort();
        serverSocket = new ServerSocket(port);

        ExecutorService executor = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                threadFactory);
        executor.execute(new Task(serverSocket.accept()));
    }

    @Override public void close(SimpleDubboURL simpleDubboURL) throws Exception {
        serverSocket.close();
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
                if (o instanceof RpcInvocation) {
                    RpcInvocation rpcInvocation = (RpcInvocation) o;
                } else {
                    throw new Exception("data is not RpcInvocation");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
