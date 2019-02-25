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
        DefaultServlet loginServlet = new LoginServlet();
        try {
            loginServlet.service(request, response);

            response.push(200);
        } catch (Exception e) {
            response.push(500);
        }
        CloseUtil.closeIO(socket);

    }
}
