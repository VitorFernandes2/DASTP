package com.poker.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils <S, T> {
    public int getMapPosition(Map<S, T> map, S key) {
        int position = 0;
        for (S index : map.keySet()) {
            if (index.equals(key)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    public Map<S, T> changeValueToTheFirstPosition(Map<S, T> map, T value, S key) {
        Map<S, T> newMap = new HashMap<>();
        newMap.put(key, value);
        map.forEach((s, t) -> {
            if (!key.equals(s)) {
                newMap.put(s,t);
            }
        });
        return newMap;
    }
}
