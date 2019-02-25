package io.github.jeesk;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);



        // 获得从浏览器中
        Socket client = server.accept();
        InputStream inputStream = client.getInputStream();
        byte[] bytes = new byte[2048];
        int len = inputStream.read(bytes);

        String requestInfo = new String(bytes, 0, len);

        server.close();
    }
}
