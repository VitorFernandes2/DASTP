package com.poker.logic.command;

import com.poker.logic.ApplicationData;

public interface ICommand {
    void execute(ApplicationData applicationData);
    String getGameName();
}
