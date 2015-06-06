package com.sleep.maket.comands;

import com.sleep.spamfilter.SpamFilter;

import java.util.List;

public class CommandStat implements ICommandHandler {
    private SpamFilter spamFilter;

    public CommandStat(SpamFilter spamFilter) {
        this.spamFilter = spamFilter;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Stat ****");
        System.out.println(spamFilter.getStat());
        return true;
    }
}
