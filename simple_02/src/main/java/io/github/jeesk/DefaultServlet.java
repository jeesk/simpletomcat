package io.github.jeesk;

public abstract class DefaultServlet {

    // 子类也可以重写该方法或
    public void service(Request req, Response resp) {
        doGet(req, resp);
        doPost(req, resp);
    }

    abstract void doGet(Request req, Response resp);

    abstract void doPost(Request req, Response resp);

}
