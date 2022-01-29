package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.utils.StringUtils;

public class CheckCommand implements ICommand {

    private final CommandAction commandAction;

    public CheckCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public CheckCommand(String commandLine) throws Exception {
        this.commandAction = convertStringToCommandAction(commandLine);
    }

    private CommandAction convertStringToCommandAction(String commandLine) throws Exception {
        String[] tokens = StringUtils.tokenizeString(commandLine);

        if (tokens.length > 1) {
            String gameName = "";
            String playerName = "";

            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");
                    gameName = parameters[1];
                } else if (tokens[i].contains("player=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "player=");
                    playerName = parameters[1];
                }
            }
            return new CommandAction(gameName, playerName, null);
        } else {
            throw new Exception("Can't create Bet Command!");
        }
    }

    @Override
    public void execute(ApplicationData applicationData) {
        //TODO: create implementation
        applicationData.getGamesList()
                .get(commandAction.getGame())
                .check(commandAction.getPlayerName());
    }

    @Override
    public void undo(ApplicationData applicationData) {

    }
}
