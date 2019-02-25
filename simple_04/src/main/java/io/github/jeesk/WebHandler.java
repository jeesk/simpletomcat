package io.github.jeesk;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WebHandler extends DefaultHandler {

    private List<Entity> entityList;
    private List<Mapping> mappingList;


    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<Mapping> mappingList) {
        this.mappingList = mappingList;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

    public String getBeginTag() {
        return beginTag;
    }

    public void setBeginTag(String beginTag) {
        this.beginTag = beginTag;
    }

    public boolean isMap() {
        return isMap;
    }

    public void setMap(boolean map) {
        isMap = map;
    }

    private Entity entity;
    private Mapping mapping;

    private String beginTag;
    private boolean isMap;

    public WebHandler() {


    }


    @Override
    public void startDocument() throws SAXException {
        // 开始文档
        entityList = new ArrayList<>();
        mappingList = new ArrayList<>();
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
    }

    @Override
    public void endDocument() throws SAXException {
        // 结束文档
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName != null) {
            beginTag = qName;
            if (qName.equalsIgnoreCase("servlet")) {
                isMap = false;
                entity = new Entity();
            } else if (qName.equals("servlet-mapping")) {
                isMap = true;
                mapping = new Mapping();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // 结束元素

        if (qName != null) {
            if (qName.equals("servlet")) {
                entityList.add(entity);
            } else if (qName.equals("servlet-mapping")) {
                mappingList.add(mapping);
            }

        }
        beginTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {


        if (beginTag != null) {

            String str = new String(ch, start, length);
            if (isMap) {
                if (beginTag.equals("servlet-name")) {
                    mapping.setName(str);
                } else if (beginTag.equals("url-pattern")) {
                    mapping.getUrlParttern().add(str);
                }

            } else {
                if (beginTag.equals("servlet-name")) {
                    entity.setName(str);
                } else if (beginTag.equals("servlet-class")) {
                    entity.setClz(str);
                }
            }


        }
    }


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        WebHandler webHandler = new WebHandler();
        try {
            saxParser = saxParserFactory.newSAXParser();
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB-INF/web.xml");

            saxParser.parse(inputStream, webHandler);


            System.out.println(1);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}
