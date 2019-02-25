package io.github.jeesk;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ServletContext {
    private Map<String, String> servlet;

    private Map<String, String> Mapping;

    public ServletContext() {
        servlet = new HashMap<>();
        Mapping = new HashMap<>();
    }
}

