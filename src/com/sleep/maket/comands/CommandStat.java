package com.sleep.maket.comands;

import com.sleep.spamfilter.Bayes;
import com.sleep.spamfilter.MessagesInfo;

import java.util.List;

public class CommandStat implements ICommandHandler {
    private Bayes bayes;

    public CommandStat(Bayes bayes) {
        this.bayes = bayes;
    }

    @Override
    public boolean execute(List<String> command) {
        System.out.println("**** Stat ****");
        System.out.println(bayes);
        System.out.println(bayes.getMessagesInfo().getSpamMessageCount() +"/"+ bayes.getMessagesInfo().getHamMessageCount());
        return true;
    }
}
