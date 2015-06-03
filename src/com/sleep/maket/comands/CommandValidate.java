package com.sleep.maket.comands;

import com.sleep.spamfilter.Bayes;

import java.util.List;

public class CommandValidate implements ICommandHandler {
    private Bayes bayes;

    public CommandValidate(Bayes bayes) {
        this.bayes = bayes;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Validate ****");
        return true;
    }
}
