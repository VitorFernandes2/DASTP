package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class JoinGameFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of joined games related entries.
     * Every log entry in which 'joinGame ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public JoinGameFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("joinGame ")).collect(Collectors.toList());
    }
}
