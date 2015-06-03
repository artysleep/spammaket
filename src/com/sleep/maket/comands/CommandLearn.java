package com.sleep.maket.comands;

import com.sleep.spamfilter.Bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLearn implements ICommandHandler {
    private Bayes bayes;

    public CommandLearn(Bayes bayes) {
        this.bayes = bayes;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Learn ****");
        Scanner input = new Scanner(System.in);
        if (command.size() > 1 && command.get(1).equals("m")) {
            System.out.println("enter message");
            String message = input.nextLine();
            if (message.equals("q")) {
                System.out.println("Learning canceled");
                input.close();
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
                    input.close();
                    return true;
            }

        } else if (command.size() > 1 && command.get(1).equals("f")) {
            System.out.println("enter file path");
            String message = input.nextLine();
            if (message.equals("q")) {
                System.out.println("Learning canceled");
                input.close();
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
                        bayes.learn(message, true);
                        break;
                    case "no":
                        bayes.learn(message, false);
                        break;
                    default:
                        System.out.println("I don't understand you. Learning canceled");
                        input.close();
                        return true;
                }
            } catch (FileNotFoundException e) {
                System.out.println("No such file. Learning canceled");
                input.close();
                return true;
            }

        } else {
            System.out.println("You should pass correct parameter f or m (file/message). Learning canceled");
        }

        input.close();
        return true;
    }
}
