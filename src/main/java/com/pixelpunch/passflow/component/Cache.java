package com.pixelpunch.passflow.component;

import com.pixelpunch.passflow.model.Password.Password;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Cache {

    private final Map<Long, Password> passwordMap = new ConcurrentHashMap<>();

    public void savePassword(Long id, Password password) {
        passwordMap.put(id, password);
    }

    public Password getPassword(Long id) {
        return passwordMap.get(id);
    }

    public void removePassword(Long id) {
        passwordMap.remove(id);
    }

    public void clearPasswords() {
        passwordMap.clear();
    }

    public int getSizeOfPasswords() {
        return passwordMap.size();
    }
}

