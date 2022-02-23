package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.logic.game.Game;
import com.poker.logic.game.state.BuyInState;
import com.poker.model.constants.Constants;
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
            throw new Exception("[Game] Invalid Bet Command!");
        else
            this.commandAction = new CommandAction(command.get(Constants.GAME_PARAMETER), command.get(Constants.PLAYER_PARAMETER),
                    Integer.parseInt(command.get(Constants.AMOUNT_PARAMETER)));
    }

    @Override
    public void execute(ApplicationData applicationData) {
        Game game = applicationData.getGamesList().get(commandAction.getGame());
        if (!game.isNull()) {
            if (game.getState() instanceof BuyInState) {
                System.out.println("[System] You cannot bet in a not started game!");
                return;
            }
            applicationData.getGamesList()
                    .get(commandAction.getGame())
                    .bet(commandAction.getPlayerName(), commandAction.getAmount());
        }
    }

    @Override
    public String getGameName() {
        return commandAction.getGame();
    }
}
