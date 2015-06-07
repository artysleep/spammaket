package com.sleep.maket.comands;

import com.sleep.spamfilter.SpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandVerifyTelephone implements ICommandHandler {
    private Scanner input;
    private SpamFilter spamFilter;

    public CommandVerifyTelephone(SpamFilter spamFilter, Scanner input) {
        this.input = input;
        this.spamFilter = spamFilter;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Check Telephone number ****");

        if (command.size() > 1 && command.get(1).equals("m")) {
            System.out.println("Enter telephone");
            String telephone = input.nextLine();
            if (telephone.equals("q")) {
                System.out.println("Checking canceled");
                return true;
            }

            System.out.printf("Telephone number in telephone: %s\n", spamFilter.verifyTelephone(telephone));
        }
        return true;
    }
}