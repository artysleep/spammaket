package com.sleep.maket.comands;

import java.util.List;

public class CommandExit implements ICommandHandler {
    private ICommandHandler saveCommand;

    public CommandExit(ICommandHandler saveCommand) {
        this.saveCommand = saveCommand;
    }

    @Override
    public boolean execute(List<String> command) {
        saveCommand.execute(null);
        System.exit(0);
        return false;
    }
}
