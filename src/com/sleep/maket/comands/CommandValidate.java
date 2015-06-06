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
                System.out.println("Learning canceled");
                return true;
            }

            System.out.printf("Probability, that it is spam: %f\n", bayes.verify(message));
            }
        else if (command.size() > 1 && command.get(1).equals("f")) {
            System.out.println("enter file path");
            String fileName = input.nextLine();
            if (fileName.equals("q")) {
                System.out.println("Learning canceled");
                return true;
            }

            try {
                List<String> messages = new ArrayList<String>();
                Scanner fileInput = new Scanner(new File(fileName), "UTF-8");
                while (fileInput.hasNextLine())
                    messages.add(fileInput.nextLine());

                for (String message : messages)
                    System.out.printf("%s\nProbability, that it is spam: %f\n\n", message, bayes.verify(message));
            } catch (FileNotFoundException e) {
                System.out.println("No such file. Learning canceled");
                return true;
            }
        } else
            System.out.println("You should pass correct parameter f or m (file/message). Learning canceled");

        return true;
    }
}