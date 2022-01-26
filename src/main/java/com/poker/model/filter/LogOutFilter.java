package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class LogOutFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of logout related entries.
     * Every log entry in which 'logout ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public LogOutFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("logout ")).collect(Collectors.toList());
    }
}
