package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class UserFilter extends FilterDecorator {
    private String name;

    /**
     * Constructor that searches the application logs for user interaction related entries.
     * Every log entry in which 'user=' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public UserFilter(ILog logs) {
        super(logs);
    }

    /**
     * Constructor that enables searches of specific users by searching for their name.
     * Every log entry in which 'user=' + name or 'host=' + name is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     * @param name {@link String} - User name.
     */
    public UserFilter(ILog logs, String name) {
        super(logs);
        this.name = name;
    }

    @Override
    public List<String> filter() {
        if (name != null) {
            return super.filter().stream().filter(log -> (log.contains("player=" + name) || log.contains("name=" + name)) || log.contains("creator=" + name)).collect(Collectors.toList());
        }
        return super.filter().stream().filter(log -> log.contains("creator=") || log.contains("player=") || log.contains("name=")).collect(Collectors.toList());
    }
}
