package com.sleep.maket.comands;

import com.sleep.spamfilter.SpamFilter;

import java.util.List;
import java.util.Scanner;

public class CommandValidate implements ICommandHandler {
    private SpamFilter spamFilter;
    private Scanner input;

    public CommandValidate(SpamFilter spamFilter, Scanner input) {
        this.spamFilter = spamFilter;
        this.input = input;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Validate ****");
        return true;
    }
}
