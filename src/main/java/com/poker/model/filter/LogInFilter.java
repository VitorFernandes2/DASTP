package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class LogInFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of login related entries.
     * Every log entry in which 'login ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public LogInFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("log ") || log.contains("login ")).collect(Collectors.toList());
    }
}
