package org.example.framework;

import java.util.List;
import java.util.Map;

public class Header {
    private final String name;
    private final List<String> value;

    public Header(String key, List<String> value) {
        this.name = key;
        this.value = value;
    }
}
