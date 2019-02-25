package io.github.jeesk;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;

@Getter
@Setter
public class Request {


    private String url;
    private String method;
    private String requestInfo; // 请求头

    private Map<String, List<String>> paramValueMap;

    private InputStream is;

    public Request() {
        url = "";
        method = "";
        requestInfo = "";
        paramValueMap = new HashMap<>();
    }

    public Request(Socket client) {
        this();
        try {
            is = client.getInputStream();
            byte[] data = new byte[20480];
            int len = is.read(data);
            requestInfo = new String(data, 0, len);
            this.parseRequestInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 创建请求后, 解析头信息
    public void parseRequestInfo() throws IOException {
//      GET /login?username=username&passwd=passwd&books=1&books=2 HTTP/1.1
//        Host: localhost:8080
//        Connection: keep-alive
//        Cache-Control: max-age=0
//        Upgrade-Insecure-Requests: 1
//        User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36
//        DNT: 1
//        Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//        Accept-Encoding: gzip, deflate, br
//        Accept-Language: zh-CN,zh;q=0.9,en-US;q=0.8,en-GB;q=0.7,en;q=0.6
        // 获得请求信息的第一行
        int inx = requestInfo.indexOf(SysCon.CRLF);
        String firstLine = requestInfo.substring(0, inx);

        int index = firstLine.indexOf("/");// 获得第一个/的索引
        method = firstLine.substring(0, index).trim(); // 截取字符串获得method
        int index2 = firstLine.indexOf("HTTP/");// 获得HTTP/所在的索引.
        String pathAndParam = firstLine.substring(index, index2);  // 访问路径   +    参数
        String[] split = pathAndParam.split("\\?"); // 通过问号分隔
        // 为了处理方便,这里暂时考虑路径和参数为空的情况
        url = split[0];
        if (split.length > 1) {
            String paramStr = split[1];
            // 解析请求参数成Map
            parseParamValue(paramStr);
        }


    }

    public void parseParamValue(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "&");
        while (stringTokenizer.hasMoreTokens()) {
            String keyAndValue = stringTokenizer.nextToken();
            String[] values = keyAndValue.split("=");
            if (values.length == 1) { // 只有key, 没有value
                Arrays.copyOf(values, 2);
                values[1] = "";
            }
            if (paramValueMap.get(values[0]) == null) { // 判断key是否存在
                paramValueMap.put(values[0], new ArrayList<String>());
            }
            paramValueMap.get(values[0]).add(values[1]);  // 将解析的valule放到对应集合中
        }
    }

    /**
     * 获得指定key的数组
     *
     * @param key
     * @return
     */
    public String[] getParameterValues(String key) {
        if (key == null) {
            throw new RuntimeException("PARAM IS NULL!");
        }
        List<String> values = paramValueMap.get(key);
        if (values == null) {
            return null;
        }
        return values.toArray(new String[values.size()]);
    }

    /**
     * 获得单个key的value值
     *
     * @param key
     * @return
     */
    public String getParameter(String key) {
        if (key == null) {
            throw new RuntimeException("PARAM IS NULL!");
        }
        String[] parameterValues = getParameterValues(key);
        if (parameterValues == null) {
            return null;
        }
        return parameterValues[0];
    }

}
