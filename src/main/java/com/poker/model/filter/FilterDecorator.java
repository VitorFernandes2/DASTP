package com.poker.model.filter;

import java.util.List;

public abstract class FilterDecorator implements ILog {
    private final ILog logs;

    public FilterDecorator(ILog logs) {
        this.logs = logs;
    }

    @Override
    public List<String> filter() {
        return logs.filter();
    }
}
