package io.github.jeesk;

import java.util.List;
import java.util.Map;

public class LoginServlet extends DefaultServlet {
    @Override
    void doGet(Request req, Response resp) {

    }
    @Override
    void doPost(Request req, Response resp) {

    }
    public void service(Request req, Response resp) {
        System.out.println(req.getMethod());
        System.out.println(req.getUrl());
        if (req.getParamValueMap().size() > 0) {
            Map<String, List<String>> paramValueMap = req.getParamValueMap();
            paramValueMap.forEach((x, y) -> {
                System.out.println(x + ":" + y);
            });
        }

        resp.print("<html><head><title>Http响应</title></head><body>Hello tomcat Http响应</body></html>");

        resp.push(200);
    }
}
