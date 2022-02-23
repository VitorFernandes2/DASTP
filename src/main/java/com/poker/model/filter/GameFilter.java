package com.poker.model.filter;

import java.util.List;
import java.util.stream.Collectors;

public class GameFilter extends FilterDecorator {
    private String gameName;

    /**
     * Constructor that searches the application logs for game related entries.
     * Every log entry in which 'gameName=' is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public GameFilter(ILog logs) {
        super(logs);
    }

    /**
     * Constructor that enables searches of specific games by searching for their name.
     * Every log entry in which 'gameName=' + gameName is present is filtered.
     *
     * @param logs {@link ILog} - Application logs.
     */
    public GameFilter(ILog logs, String gameName) {
        super(logs);
        this.gameName = gameName;
    }

    @Override
    public List<String> filter() {
        if (gameName != null) {
            return super.filter().stream().filter(log -> log.contains("game=" + gameName)).collect(Collectors.toList());
        }
        return super.filter().stream().filter(log -> log.contains("game=")).collect(Collectors.toList());
    }
}
