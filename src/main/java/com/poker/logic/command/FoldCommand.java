package com.poker.logic.command;

import com.poker.logic.ApplicationData;

public class FoldCommand implements ICommand {

    private final CommandAction commandAction;

    public FoldCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    @Override
    public void execute(ApplicationData applicationData) {

    }

    @Override
    public void undo(ApplicationData applicationData) {

    }
}
