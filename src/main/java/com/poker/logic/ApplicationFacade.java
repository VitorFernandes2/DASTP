package com.poker.logic;

import com.poker.logic.command.CommandAdapter;
import com.poker.logic.command.CommandManager;

public class ApplicationFacade {
    private final ApplicationData data;
    private final CommandManager commandManager;

    public ApplicationFacade(ApplicationData data) {
        this.data = data;
        this.commandManager = new CommandManager(data);
    }

    public boolean addUser(String commandLine) {
        return CommandAdapter.addUser(commandLine, data.getOnlinePlayers());
    }

    public boolean loginUser(String commandLine) {
        return CommandAdapter.loginUser(commandLine, data.getOnlinePlayers());
    }

    public void buyChips(String commandLine) {
        CommandAdapter.buyChips(commandLine, data.getOnlinePlayers());
    }

    public boolean createFriendlyGame(String commandLine) {
        return CommandAdapter.createFriendlyGame(commandLine, data);
    }
}
