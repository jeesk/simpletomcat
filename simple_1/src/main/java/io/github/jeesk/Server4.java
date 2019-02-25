package io.github.jeesk;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Server4 {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        // 获得从浏览器中请求,
        Socket client = server.accept();


        Request request = new Request(client);
         System.out.println(request.getMethod());
        System.out.println(request.getUrl());
        if (request.getParamValueMap().size() > 0) {
            Map<String, List<String>> paramValueMap = request.getParamValueMap();
            paramValueMap.forEach((x, y) -> {
                System.out.println(x + ":" + y);
            });
        }

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


