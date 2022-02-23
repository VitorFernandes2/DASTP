package com.poker.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class CollectionUtils {

    /**
     * This method only is prepared to receive lists and queues.
     * The main purpose of this method is to maintain the collection order.
     *
     * @param collection    Collection<String>
     * @param oldName       String
     * @param newName       String
     */
    public static void changeNameInCollection(Collection<String> collection, String oldName, String newName) {
        Collection<String> copy = null;
        if (collection instanceof List) {
            copy = new ArrayList<>(collection);
        } else if (collection instanceof Queue) {
            copy = new ArrayDeque<>(collection);
        }

        if(copy == null) return;
        collection.clear();
        copy.forEach(s -> {
            if(s.equals(oldName)) {
                collection.add(newName);
            } else {
                collection.add(s);
            }
        });
    }
}
