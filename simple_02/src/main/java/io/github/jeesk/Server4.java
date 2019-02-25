package io.github.jeesk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server4 {

    private static ExecutorService executor = Executors.newFixedThreadPool(20); // 增加线程池

    private boolean flag = true;
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        new Server4().start();
    }

    public void start() throws IOException {
        this.start(8080);
    }


    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.run();
    }

    public void run() throws IOException {
        while (flag) {
            try {
                executor.execute(new Dispatcher(serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
                serverSocket.close();
            }
        }
    }
}


