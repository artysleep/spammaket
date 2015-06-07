package com.sleep.maket.comands;

import com.sleep.spamfilter.SURBL;
import com.sleep.spamfilter.TBL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandTBL implements ICommandHandler {
    private Scanner input;
    private TBL tbl;

    public CommandTBL(TBL tbl, Scanner input) {
        this.input = input;
        this.tbl = tbl;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Check Telephone number ****");

        if (command.size() > 1 && command.get(1).equals("m")) {
            System.out.println("Enter message");
            String message = input.nextLine();
            if (message.equals("q")) {
                System.out.println("Checking canceled");
                return true;
            }

            System.out.printf("Telephone number in message: %s\n", tbl.extractTelephones(message));
        }
        else if (command.size() > 1 && command.get(1).equals("f")) {
            System.out.println("enter file path");
            String fileName = input.nextLine();
            if (fileName.equals("q")) {
                System.out.println("Telephone number checking canceled");
                return true;
            }

            try {
                List<String> messages = new ArrayList<String>();
                Scanner fileInput = new Scanner(new File(fileName), "UTF-8");
                while (fileInput.hasNextLine())
                    messages.add(fileInput.nextLine());

                for (String message : messages)
                    System.out.printf("%s\nTelephone number in messages: %s\n", message, tbl.extractTelephones(message));
            } catch (FileNotFoundException e) {
                System.out.println("No such file. Telephone number checking canceled");
                return true;
            }
        } else
            System.out.println("You should pass correct parameter f or m (file/message). Telephone number checking canceled");

        return true;
    }
}