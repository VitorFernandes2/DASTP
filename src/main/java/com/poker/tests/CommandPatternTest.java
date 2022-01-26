package com.poker.tests;

import com.poker.logic.ApplicationData;
import com.poker.logic.command.*;

public class CommandPatternTest {
    public static void main(String[] args) {
        ApplicationData applicationData = ApplicationData.getInstance();
        CommandManager commandManager = new CommandManager(applicationData);
        ICommand command = new BetCommand(new CommandAction("jogo1", "Vitor", 5.0)); // Example: bet jogo1 Vitor 5
        commandManager.apply(command);
        commandManager.undo();

        ICommand command2 = new CheckCommand(new CommandAction("jogo1", "Vitor", null));
        commandManager.apply(command2);
    }
}
