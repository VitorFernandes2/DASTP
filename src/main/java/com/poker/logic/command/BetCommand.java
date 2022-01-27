package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.utils.StringUtils;

public class BetCommand implements ICommand {

    private final CommandAction commandAction;

    public BetCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public BetCommand(String commandLine) throws Exception {
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
                } else if (tokens[i].contains("value=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "value=");
                    String valueStr = parameters[1];
                    value = Double.parseDouble(valueStr);
                }
            }
            return new CommandAction(gameName, playerName, value);
        } else {
            throw new Exception("Can't create Bet Command!");
        }
    }

    @Override
    public void execute(ApplicationData applicationData) {
        //TODO: create validations for the bets
        applicationData.getGamesList()
                .get(commandAction.getGame())
                .bet(commandAction.getPlayerName(), commandAction.getAmount());
    }

    @Override
    public void undo(ApplicationData applicationData) {

    }
}
