package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.utils.StringUtils;

import java.util.Map;

public class CheckCommand implements ICommand {

    private final CommandAction commandAction;

    public CheckCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public CheckCommand(String commandLine) throws Exception {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        if (command.size() < 4)
            throw new Exception("[Game] Invalid Check Command!");
        else
            this.commandAction = new CommandAction(command.get("game"), command.get("player"), null);
    }

    @Override
    public void execute(ApplicationData applicationData) {
        if(!applicationData.getGamesList().get(commandAction.getGame()).isNull()) {
            applicationData.getGamesList()
                    .get(commandAction.getGame())
                    .check(commandAction.getPlayerName());
        }
    }

    @Override
    public String getGameName() {
        return commandAction.getGame();
    }
}
