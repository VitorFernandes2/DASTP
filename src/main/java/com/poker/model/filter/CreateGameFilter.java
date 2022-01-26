package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class CreateGameFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of created games related entries.
     * Every log entry in which 'createGame ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public CreateGameFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("createGame ")).collect(Collectors.toList());
    }
}
