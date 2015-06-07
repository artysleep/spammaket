package com.sleep.maket.comands;

import com.sleep.spamfilter.SpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandSURBL implements ICommandHandler {
    private Scanner input;
    private SpamFilter spamFilter;

    public CommandSURBL(SpamFilter spamFilter, Scanner input) {
        this.input = input;
        this.spamFilter = spamFilter;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** CheckURL ****");

        if (command.size() > 1 && command.get(1).equals("m")) {
            System.out.println("enter message");
            String message = input.nextLine();
            if (message.equals("q")) {
                System.out.println("Learning canceled");
                return true;
            }

            System.out.printf("URL in message: %s\n", spamFilter.verifyUrls(message));
        }
        else if (command.size() > 1 && command.get(1).equals("f")) {
            System.out.println("enter file path");
            String fileName = input.nextLine();
            if (fileName.equals("q")) {
                System.out.println("URL checking canceled");
                return true;
            }

            try {
                List<String> messages = new ArrayList<String>();
                Scanner fileInput = new Scanner(new File(fileName), "UTF-8");
                while (fileInput.hasNextLine())
                    messages.add(fileInput.nextLine());

                //for (String message : messages)
                   // System.out.printf("%s\nURLs in messages: %s\n", message, spamFilter.extractUrls(message));
            } catch (FileNotFoundException e) {
                System.out.println("No such file. URL checking");
                return true;
            }
        } else
            System.out.println("You should pass correct parameter f or m (file/message). URL checking canceled");

        return true;
    }
}