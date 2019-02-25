package io.github.jeesk;

import java.util.ArrayList;
import java.util.List;

public class Mapping {
    private String name;
    private List<String> urlParttern;


    public Mapping() {
        urlParttern = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlParttern() {
        return urlParttern;
    }

    public void setUrlParttern(List<String> urlParttern) {
        this.urlParttern = urlParttern;
    }
}
