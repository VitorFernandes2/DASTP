package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of register related entries.
     * Every log entry in which 'register ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public RegisterFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("register ")).collect(Collectors.toList());
    }
}
