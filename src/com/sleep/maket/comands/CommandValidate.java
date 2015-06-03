package com.sleep.maket.comands;

import com.sleep.spamfilter.Bayes;

import java.util.List;
import java.util.Scanner;

public class CommandValidate implements ICommandHandler {
    private Bayes bayes;
    private Scanner input;

    public CommandValidate(Bayes bayes, Scanner input) {
        this.bayes = bayes;
        this.input = input;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Validate ****");
        return true;
    }
}
