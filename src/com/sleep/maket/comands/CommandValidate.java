package com.sleep.maket.comands;

import com.sleep.spamfilter.SpamFilter;
import com.sleep.spamfilter.bayes.BayesFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandValidate implements ICommandHandler {
    SpamFilter bayes;
    private Scanner input;

    public CommandValidate(SpamFilter bayes, Scanner input) {
        this.bayes = bayes;
        this.input = input;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Validate ****");

        if (command.size() > 1 && command.get(1).equals("m")) {
            System.out.println("enter message");
            String message = input.nextLine();
            if (message.equals("q")) {
                System.out.println("Validating canceled");
                return true;
            }
            System.out.println("enter phone");
            String phone = input.nextLine();
            if (phone.equals("q")) {
                System.out.println("Validating canceled");
                return true;
            }

            System.out.printf("Probability, that it is spam: %d\n", bayes.verify(phone, message));
            }
        else if (command.size() > 1 && command.get(1).equals("f")) {
            System.out.println("enter file path");
            String fileName = input.nextLine();
            if (fileName.equals("q")) {
                System.out.println("Validating canceled");
                return true;
            }

            try {
                Scanner fileInput = new Scanner(new File(fileName), "UTF-8");
                while (fileInput.hasNextLine()){
                    String line = fileInput.nextLine();
                    int delPos = line.indexOf(":");
                    String phone = line.substring(0, delPos);
                    String message = line.substring(delPos+1);
                    System.out.printf("%s\n Probability that this message is spam: %d\n", message, bayes.verify(phone, message));
                }

            } catch (FileNotFoundException e) {
                System.out.println("No such file. Validating canceled");
                return true;
            }
        } else
            System.out.println("You should pass correct parameter f or m (file/message). Validating canceled");

        return true;
    }
}