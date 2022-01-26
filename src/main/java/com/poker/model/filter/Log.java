package com.poker.model.filter;

import java.util.List;

public class Log implements ILog {
    private final List<String> logs;

    public Log(List<String> logs) {
        this.logs = logs;
    }

    /**
     * Function gets all the logs from the database / application.
     *
     * @return List<String> - ArrayList with all the application logs.
     */
    @Override
    public List<String> filter() {
        return logs;
    }
}
