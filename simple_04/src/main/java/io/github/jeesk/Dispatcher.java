package io.github.jeesk;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private Request request;
    private Response response;
    private int port;
    private Socket socket;

    public Dispatcher() {

    }

    public Dispatcher(Socket socket) throws IOException {
        this();
        this.socket = socket;
        response = new Response(socket);
        request = new Request(socket);

    }

    @Override
    public void run() {

        DefaultServlet servlet = null;
        try {
            servlet = WebApp.getServlet(request.getUrl());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (servlet == null) {
            // 跳转到登录页面
            servlet = new IndexServlet();

        }
        try {
            servlet.service(request, response);

            response.push(200);
        } catch (Exception e) {
            response.push(500);
        }


        CloseUtil.closeIO(socket);

    }
}
