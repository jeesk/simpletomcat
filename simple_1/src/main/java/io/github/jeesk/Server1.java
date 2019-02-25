package io.github.jeesk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server1 {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);


        // 获得从浏览器中请求,
        Socket client = server.accept();

        // 获得请求中的信息
        InputStream inputStream = client.getInputStream();
        byte[] bytes = new byte[2048];
        int len = inputStream.read(bytes);
        String requestInfo = new String(bytes, 0, len);
        System.out.println(requestInfo);



        server.close();
        client.close();
    }
}


