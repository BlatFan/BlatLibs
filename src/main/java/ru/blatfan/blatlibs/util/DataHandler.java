package ru.blatfan.blatlibs.util;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public class DataHandler {

    private Map<UUID, Map<String, Object>> globalDataMap;

    public void addData(UUID uuid, String subKey, Object value) {
        globalDataMap.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>()).put(subKey, value);
    }

    public void removeData(UUID uuid, String subKey) {
        Map<String, Object> subMap = globalDataMap.get(uuid);

        if (subMap != null) {
            subMap.remove(subKey);

            if (subMap.isEmpty()) {
                globalDataMap.remove(uuid);
            }
        }
    }

    public Object getData(UUID uuid, String subKey) {
        return globalDataMap.getOrDefault(uuid, Collections.emptyMap()).get(subKey);
    }

    public void clearData(UUID uuid) {
        globalDataMap.remove(uuid);
    }
}
