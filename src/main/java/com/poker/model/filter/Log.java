package com.poker.model.filter;

import java.util.ArrayList;
import java.util.List;

public class Log implements ILog {
    private static List<String> logs;
    private static Log logInstance = null;

    private Log() {
        if (logs == null) {
            logs = new ArrayList<>();
        }
    }

    public static Log getInstance() {
        if (logInstance == null)
            logInstance = new Log();

        return logInstance;
    }

    /**
     * Function to add and show the last action and system feedback into log entries.
     *
     * @param log {@link String}
     */
    public void addAndShowLog(String log) {
        System.out.println(log);
        addLog(log);
    }

    /**
     * Function to add the last action and system feedback into log entries.
     *
     * @param log {@link String}
     */
    public void addLog(String log) {
        logs.add(log);
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
