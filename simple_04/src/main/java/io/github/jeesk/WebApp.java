package io.github.jeesk;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WebApp {
    private static ServletContext servletContext;

    static {
        servletContext = new ServletContext();


        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        WebHandler webHandler = new WebHandler();
        try {
            saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB-INF/web.xml"), webHandler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }


        // 放入Servlet 映射和Mapping

        Map<String, String> servlet = servletContext.getServlet();
        for (Entity entity : webHandler.getEntityList()) {
            servlet.put(entity.getName(), entity.getClz());
        }


        Map<String, String> mapping = servletContext.getMapping();
        for (Mapping mapping1 : webHandler.getMappingList()) {
            List<String> urlParttern = mapping1.getUrlParttern();
            for (String s : urlParttern) {
                // 放入url, 和Servlet的名字
                mapping.put(s, mapping1.getName()); //放入路劲映射
            }
        }


    }

    public static DefaultServlet getServlet(String url) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (null == url || "".equals(url)) {
            return null;
        }


        String s = servletContext.getMapping().get(url);

        Map<String, String> servlet = servletContext.getServlet();
        // 反射创建Servlet

        String servletFullName = servlet.get(s);
        if (servletFullName == null) {
            return null;
        }

        return (DefaultServlet) Class.forName(servlet.get(s)).newInstance();

    }


}
