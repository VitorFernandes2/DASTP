package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class BetFilter extends FilterDecorator {
    private String value;

    /**
     * Constructor that searches the application logs for betting related entries.
     * Every log entry in which 'bet ' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public BetFilter(ILog logs) {
        super(logs);
    }

    /**
     * Constructor that enables searches of specific betting values by searching for their value.
     * Every log entry in which 'bet ' and 'value=' + value is present is filtered.
     *
     * @param logs  {@link ILog} - Application logs.
     * @param value {@link Integer} - Betting value.
     */
    public BetFilter(ILog logs, Integer value) {
        super(logs);
        this.value = Integer.toString(value);
    }

    @Override
    public List<String> filter() {
        if (value != null) {
            return super.filter().stream().filter(log -> (log.contains("bet ") && log.contains("value=" + value))).collect(Collectors.toList());
        }
        return super.filter().stream().filter(log -> log.contains("bet ")).collect(Collectors.toList());
    }
}
