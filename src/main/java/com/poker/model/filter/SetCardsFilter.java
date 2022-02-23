package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class SetCardsFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of setCards for debugging related entries.
     * Every log entry in which 'setCards ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public SetCardsFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("sc ") || log.contains("setCards ")).collect(Collectors.toList());
    }
}
