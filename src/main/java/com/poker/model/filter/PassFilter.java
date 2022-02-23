package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class PassFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of pass related entries.
     * Every log entry in which 'pass ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public PassFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("check ")).collect(Collectors.toList());
    }
}
