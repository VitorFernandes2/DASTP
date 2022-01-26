package com.poker.logic.command;

import com.poker.logic.ApplicationData;

public class BetCommand implements ICommand {

    private CommandAction commandAction;

    public BetCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    @Override
    public void execute(ApplicationData applicationData) {
        // Example for implement with the state machine
        //Game game = applicationData.getGamesList().stream().findFirst().map((game -> game.getName == commandAction.getGame()));
        //game.bet(commandAction.getPlayer(), commandAction.getAmount());
    }

    @Override
    public void undo(ApplicationData applicationData) {

    }
}
