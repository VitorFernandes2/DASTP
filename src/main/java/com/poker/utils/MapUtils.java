package com.poker.utils;

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
}
