package io.github.jeesk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private int contengLength; // 响应内容字节长度
    private StringBuilder content;
    private StringBuilder headInfo;
    private BufferedWriter bw;

    public Response() {
        contengLength = 0;
        content = new StringBuilder();
        headInfo = new StringBuilder();
    }
    public Response(Socket client) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Response createInfo(int code) {
        headInfo.append("HTTP/1.1").append(SysCon.BLANK).append(code).append(SysCon.BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK");
                break;
            case 500:
                headInfo.append("SERVER IS ERROR");
                break;
            case 404:
                headInfo.append(" NOT FOUND!");
                break;
            default:
                break;
        }
        headInfo.append(SysCon.CRLF);
        // 响应头
        headInfo.append("Server: Simple_Tomcat Server/0.0.1").append(SysCon.CRLF);
        headInfo.append("DATE:").append(new Date()).append(SysCon.CRLF);
        // text/json text/html
        headInfo.append("Content-type: text/html;charset=utf-8;").append(SysCon.CRLF);
        // 响应内容长度, 作为变量可以随时改变
        headInfo.append("Content-Length:").append(content.toString().getBytes().length).append(SysCon.CRLF);
        return this;
    }

    public Response println(String appendContent) {
        if (appendContent == null) {
            throw new RuntimeException("appendContent is null ");
        }
        content.append(appendContent).append(SysCon.CRLF);
        contengLength += (appendContent + SysCon.CRLF).getBytes().length;
        return this;
    }
    public Response print(String appendContent) {
        if (appendContent == null) {
            throw new RuntimeException("appendContent is null ");
        }
        content.append(appendContent);
        contengLength += appendContent.getBytes().length;
        return this;
    }
    // 推送出去
    public void push(int code) {
        this.createInfo(code);
        try {
            bw.append(headInfo);
            bw.append(content);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
