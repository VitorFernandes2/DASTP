package com.poker.logic.command;

import com.poker.logic.ApplicationData;

public class CheckCommand implements ICommand {

    private CommandAction commandAction;

    public CheckCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    @Override
    public void execute(ApplicationData applicationData) {

    }

    @Override
    public void undo(ApplicationData applicationData) {

    }
}
