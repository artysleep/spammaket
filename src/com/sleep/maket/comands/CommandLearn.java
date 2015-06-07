package com.sleep.maket.comands;

import com.sleep.spamfilter.SpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLearn implements ICommandHandler {
    private SpamFilter spamFilter;
    private Scanner input;

    public CommandLearn(SpamFilter bayes, Scanner input) {
        this.spamFilter = bayes;
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
            System.out.println("enter phone");
            String phone = input.nextLine();
            if (phone.equals("q")) {
                System.out.println("Learning canceled");
                return true;
            }
            return learnFromMessage(phone, message);

        } else if (command.size() > 1 && command.get(1).equals("f")) {
            System.out.println("enter file path");
            String fileName = input.nextLine();
            if (fileName.equals("q")) {
                System.out.println("Learning canceled");
                return true;
            }
            return learnFromFile(fileName);
        } else {
            System.out.println("You should pass correct parameter f or m (file/message). Learning canceled");
        }

        return true;
    }

    private boolean learnFromMessage(String phone, String message) {
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
                spamFilter.learn(phone, message, true);
                break;
            case "no":
                spamFilter.learn(phone, message, false);
                break;
            default:
                System.out.println("I don't understand you. Learning canceled");
                return true;
        }
        return false;
    }

    public boolean learnFromFile(String fileName) {
        try {
            List<String> messages = new ArrayList<String>();
            Scanner fileInput = new Scanner(new File(fileName), "UTF-8");
            while (fileInput.hasNextLine()) {
                messages.add(fileInput.nextLine());
            }
            System.out.println("\nCount of messages: " + messages.size() + "\n");
            System.out.println("is it spam?");
            String spamOrNot = input.nextLine();
            spamOrNot.replace("t","yes");
            spamOrNot.replace("yes", "y");
            spamOrNot.replace("yes", "true");
            spamOrNot.replace("no", "n");
            spamOrNot.replace("no", "f");
            spamOrNot.replace("no", "false");
            switch (spamOrNot) {
                case "yes":
                    spamFilter.learn(messages, true);
                    break;
                case "no":
                    spamFilter.learn(messages, false);
                    break;
                default:
                    System.out.println("I don't understand you. Learning canceled");
                    return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file. Learning canceled");
            return true;
        }
        return true;
    }
}
