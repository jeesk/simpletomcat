package io.github.jeesk;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class IndexServlet extends DefaultServlet {
    @Override
    void doGet(Request req, Response resp) {

    }

    @Override
    void doPost(Request req, Response resp) {

    }

    public void service(Request req, Response resp) {


        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream( "index.html");
        byte[] data = new byte[2048];
        try {
            int len = is.read(data);
            String indexString = new String(data, 0, len);
            resp.print(indexString);

            resp.push(200);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
