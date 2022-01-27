package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.utils.StringUtils;

public class FoldCommand implements ICommand {

    private final CommandAction commandAction;

    public FoldCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public FoldCommand(String commandLine) throws Exception {
        this.commandAction = convertStringToCommandAction(commandLine);
    }

    private CommandAction convertStringToCommandAction(String commandLine) throws Exception {
        String[] tokens = StringUtils.tokenizeString(commandLine);

        if (tokens.length > 1) {
            String gameName = "";
            String playerName = "";
            double value = 0;

            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");
                    gameName = parameters[1];
                } else if (tokens[i].contains("player=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "player=");
                    playerName = parameters[1];
                }
            }
            return new CommandAction(gameName, playerName, value);
        } else {
            throw new Exception("Can't create Bet Command!");
        }
    }

    @Override
    public void execute(ApplicationData applicationData) {
        //TODO: Create implementation
        applicationData.getGamesList()
                .get(commandAction.getGame())
                .fold(commandAction.getPlayerName(), commandAction.getAmount());
    }

    @Override
    public void undo(ApplicationData applicationData) {

    }
}
