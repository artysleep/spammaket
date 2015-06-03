package com.sleep.maket.comands;

import com.sleep.spamfilter.Bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLearn implements ICommandHandler {
    private Bayes bayes;
    private Scanner input;

    public CommandLearn(Bayes bayes, Scanner input) {
        this.bayes = bayes;
        this.input = input;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Learn ****");
        if (command.size() > 1 && command.get(1).equals("m")) {
            System.out.println("enter message");
            String message = input.nextLine();
            if (message.equals("q")) {
                System.out.println("Learning canceled");
                return true;
            }
            System.out.println("is it spam?");
            String spamOrNot = input.nextLine();
            spamOrNot.replace("yes", "y");
            spamOrNot.replace("yes", "t");
            spamOrNot.replace("yes", "true");
            spamOrNot.replace("no", "n");
            spamOrNot.replace("no", "f");
            spamOrNot.replace("no", "false");
            switch (spamOrNot) {
                case "yes":
                    bayes.learn(message, true);
                    break;
                case "no":
                    bayes.learn(message, false);
                    break;
                default:
                    System.out.println("I don't understand you. Learning canceled");
                    return true;
            }

        } else if (command.size() > 1 && command.get(1).equals("f")) {
            System.out.println("enter file path");
            String message = input.nextLine();
            if (message.equals("q")) {
                System.out.println("Learning canceled");
                return true;
            }

            try {
                List<String> messages = new ArrayList<String>();
                Scanner fileInput = new Scanner(new File("spam.txt"), "UTF-8");
                while (fileInput.hasNextLine()) {
                    messages.add(fileInput.nextLine());
                }
                System.out.println("is it spam?");
                String spamOrNot = input.nextLine();
                spamOrNot.replace("yes", "t");
                spamOrNot.replace("yes", "y");
                spamOrNot.replace("yes", "true");
                spamOrNot.replace("no", "n");
                spamOrNot.replace("no", "f");
                spamOrNot.replace("no", "false");
                switch (spamOrNot) {
                    case "yes":
                        bayes.learn(messages, true);
                        break;
                    case "no":
                        bayes.learn(messages, false);
                        break;
                    default:
                        System.out.println("I don't understand you. Learning canceled");
                        return true;
                }
            } catch (FileNotFoundException e) {
                System.out.println("No such file. Learning canceled");
                return true;
            }

        } else {
            System.out.println("You should pass correct parameter f or m (file/message). Learning canceled");
        }

        return true;
    }
}
