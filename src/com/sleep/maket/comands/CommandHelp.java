package com.sleep.maket.comands;

import java.util.List;

public class CommandHelp implements ICommandHandler {

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Help ****");
        System.out.println("helpme/help/man/? - Use it to watch manual page");
        System.out.println("learn f/m - learn baes filter with file or message");
        System.out.println("validate - test filter on message");
        System.out.println("stat - print bayes stats");
        System.out.println("url - print URLs in message");
        System.out.println("tbl - print Telephone numbers s in message");

        return true;
    }
}
