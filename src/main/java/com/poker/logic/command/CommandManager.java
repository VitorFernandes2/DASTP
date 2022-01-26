package com.poker.logic.command;

import com.poker.logic.ApplicationData;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    ApplicationData applicationData;
    List<ICommand> undoList = new ArrayList<>();
    List<ICommand> redoList = new ArrayList<>();

    public CommandManager(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public void apply(ICommand command) {
        command.execute(applicationData);
        undoList.add(command);
        redoList.clear();
    }

    public void undo() {
        if(undoList.isEmpty()) return;
        ICommand lastCommand = undoList.remove(undoList.size() - 1);
        lastCommand.undo(applicationData);
        redoList.add(lastCommand);
    }
}
