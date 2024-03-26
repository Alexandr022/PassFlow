package com.pixelpunch.passflow.component;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseCache {

    private final Map<String, Object> responseMap = new ConcurrentHashMap<>();

    public void saveResponse(String key, Object value) {
        responseMap.put(key, value);
    }

    public Object getResponse(String key) {
        return responseMap.get(key);
    }

    public void removeResponse(String key) {
        responseMap.remove(key);
    }

    public void clearResponses() {
        responseMap.clear();
    }

    public int getSizeOfResponses() {
        return responseMap.size();
    }
}