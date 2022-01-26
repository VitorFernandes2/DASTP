package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class FoldFilter extends FilterDecorator {
    /**
     * Constructor that searches the application logs of fold related entries.
     * Every log entry in which 'fold ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public FoldFilter(ILog logs) {
        super(logs);
    }

    @Override
    public List<String> filter() {
        return super.filter().stream().filter(log -> log.contains("fold ")).collect(Collectors.toList());
    }
}
