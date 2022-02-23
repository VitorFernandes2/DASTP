package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class QuitFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of quit related entries.
     * Every log entry in which 'quit ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public QuitFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("fold ")).collect(Collectors.toList());
    }
}
