package io.github.jeesk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server2 {
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

        StringBuilder content = new StringBuilder();
        content.append("<html><head><title>Http响应</title></head><body>Hello tomcat Http响应</body></html>;" + "");

        // 如果使用下面的json形式, 那么僵Content-type: 里面的html变成json即可.
        // content.append(" {\"name\":\"小强\",\"age\":16,\"msg\":[\"a\",\"b\"],\"regex\": \"^http://.*\"}");

        StringBuilder resp = new StringBuilder();
        // HTTP协议版本   状态码,描述
        resp.append("HTTP/1.1").append(SysCon.BLANK).append("200").append(SysCon.BLANK).append("OK").append(SysCon.CRLF);
        // 响应头
        resp.append("Server: Simple_Tomcat Server/0.0.1").append(SysCon.CRLF);
        resp.append("DATE:").append(new Date()).append(SysCon.CRLF);
        // text/json text/html
        resp.append("Content-type: text/html;charset=utf-8;").append(SysCon.CRLF);
        // 响应内容长度
        resp.append("Content-Length:").append(content.toString().getBytes().length).append(SysCon.CRLF);
       resp.append(SysCon.CRLF);
        resp.append(content);


        // 输出到网页上面
        BufferedWriter bufferedReader = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        bufferedReader.write(resp.toString());
        bufferedReader.flush();

        server.close();
        client.close();
    }
}


