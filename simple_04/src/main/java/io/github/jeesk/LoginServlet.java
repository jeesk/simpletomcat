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

        String username = req.getParameter("username").trim();
        String passwd = req.getParameter("passwd").trim();

        if (username.equals("xiaoming") && passwd.equals("12345")) {
            resp.print("<html><head><title>Http响应</title></head><body>登陆成功</body></html>");
        } else {
            resp.print("<html><head><title>Http响应</title></head><body>登陆失败,密码错误</body></html>");
        }

        resp.push(200);
    }
}
