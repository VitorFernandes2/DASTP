package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class StartGameFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of started games related entries.
     * Every log entry in which 'startGame ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public StartGameFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("startGame ")).collect(Collectors.toList());
    }
}
