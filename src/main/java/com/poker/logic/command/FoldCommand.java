package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.utils.StringUtils;

import java.util.Map;

public class FoldCommand implements ICommand {

    private final CommandAction commandAction;

    public FoldCommand(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public FoldCommand(String commandLine) throws Exception {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        if (command.size() < 4)
            throw new Exception("[Game] Invalid Fold Command!");
        else
            this.commandAction = new CommandAction(command.get("game"), command.get("player"), null);
    }

    @Override
    public void execute(ApplicationData applicationData) {
        if(!applicationData.getGamesList().get(commandAction.getGame()).isNull()) {
            applicationData.getGamesList()
                    .get(commandAction.getGame())
                    .fold(commandAction.getPlayerName());
        }
    }

    @Override
    public String getGameName() {
        return commandAction.getGame();
    }
}
