package com.sleep.maket.comands;

import com.sleep.spamfilter.Bayes;

import java.util.List;

public class CommandStat implements ICommandHandler {
    private Bayes bayes;

    public CommandStat(Bayes bayes) {
        this.bayes = bayes;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Stat ****");
        System.out.println(bayes);

        return true;
    }
}
