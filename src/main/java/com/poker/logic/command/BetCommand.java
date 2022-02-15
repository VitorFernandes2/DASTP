package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.utils.StringUtils;

import java.util.Map;

public class BetCommand implements ICommand {

    private final CommandAction commandAction;

    public BetCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public BetCommand(String commandLine) throws Exception {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        if (command.size() < 5)
            throw new Exception("Can't create Bet Command!");
        else
            this.commandAction = new CommandAction(command.get("game"), command.get("player"),
                    Integer.parseInt(command.get("amount")));
    }

    @Override
    public void execute(ApplicationData applicationData) {
        applicationData.getGamesList()
                .get(commandAction.getGame())
                .bet(commandAction.getPlayerName(), commandAction.getAmount());
    }

    @Override
    public String getGameName() {
        return commandAction.getGame();
    }
}
