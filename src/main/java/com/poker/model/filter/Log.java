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
     * Function to add new successful actions and system commands into log entries.
     *
     * @param command {@link String} - Command inputted into to the system's command line.
     */
    public void addLog(String command) {
        logs.add(command);
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
