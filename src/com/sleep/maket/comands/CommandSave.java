package com.sleep.maket.comands;

import com.sleep.spamfilter.Bayes;
import com.sleep.spamfilter.Exchanger;
import com.sleep.spamfilter.MessagesInfo;
import com.sleep.spamfilter.WordInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Артем on 05.06.2015.
 */
public class CommandSave implements ICommandHandler {


    private Bayes bayes;

    public CommandSave(Bayes bayes) {
        this.bayes = bayes;
    }

    @Override
    public boolean execute(List<String> command) {
        Exchanger exchanger = new Exchanger();
        MessagesInfo messagesInfo = bayes.getMessagesInfo();
        Map<String, WordInfo> words = bayes.getExportWordsInfo();

        exchanger.exportMessagesInfo(messagesInfo);
        exchanger.exportWordInfo(words);

        exchanger.destroy();
        return true;
    }
}
