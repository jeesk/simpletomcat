package io.github.jeesk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server3 {
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
        content.append("<html><head><title>Http响应</title></head><body>Hello tomcat , 你好 Tomcat</body></html>" );

        // 如果使用下面的json形式, 那么僵Content-type: 里面的html变成json即可.
        // content.append(" {\"name\":\"小强\",\"age\":16,\"msg\":[\"a\",\"b\"],\"regex\": \"^http://.*\"}");
        Response response = new Response(client);
        // 在网页上面要显示的内容
        response.print("<html><head><title>Http响应</title></head><body>Hello tomcat Http响应</body></html>");
        response.push(200);
        server.close();
        client.close();
    }
}


