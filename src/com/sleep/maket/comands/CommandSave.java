package com.sleep.maket.comands;

import com.sleep.spamfilter.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ����� on 05.06.2015.
 */
public class CommandSave implements ICommandHandler {


    private SpamFilter spamFilter;

    public CommandSave(SpamFilter spamFilter) {
        this.spamFilter = spamFilter;
    }

    @Override
    public boolean execute(List<String> command) {
        spamFilter.save();

        return true;
    }
}
