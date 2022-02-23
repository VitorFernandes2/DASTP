package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class ChangeCardsFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of changeCards for debugging related entries.
     * Every log entry in which 'changeCards ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public ChangeCardsFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("sc ") || log.contains("setCards ")).collect(Collectors.toList());
    }
}
