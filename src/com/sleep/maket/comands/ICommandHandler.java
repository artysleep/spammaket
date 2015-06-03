package com.sleep.maket.comands;

import java.util.List;

public interface ICommandHandler {
    public boolean execute(List<String> command);
}
