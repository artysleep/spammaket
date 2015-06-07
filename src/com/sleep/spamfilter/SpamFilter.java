package com.sleep.spamfilter;

import com.sleep.spamfilter.bayes.BayesFilter;
import com.sleep.spamfilter.bayes.MessagesInfo;
import com.sleep.spamfilter.bayes.WordInfo;

import java.util.List;
import java.util.Map;

public class SpamFilter {
    private BayesFilter bayesFilter;
    private SURBL surbl;

    public SpamFilter() {
        Exchanger exchanger = new Exchanger();

        MessagesInfo messagesInfo = exchanger.importMessagesInfo();
        Map<String, WordInfo> words = exchanger.importWordInfo();
        exchanger.destroy();

        this.bayesFilter = new BayesFilter(messagesInfo, words);
    }

    public void learn(List<String> messages, boolean isSpam) {
        for (String phrase : messages) {
            learn(phrase, isSpam);
        }
    }

    public void learn(String message, boolean isSpam) {
        bayesFilter.learn(message, isSpam);
        bayesFilter.getMessagesInfo().incCounter(1, isSpam);
    }

    public double verify(String message) {
        double spamProb = bayesFilter.verify(message);
        return spamProb;
    }

    public List<String> surbl(String message) {
        //surbl.extractUrls(message);
        return surbl.extractUrls(message);
    }


    public void save() {
        Exchanger exchanger = new Exchanger();
        MessagesInfo messagesInfo = bayesFilter.getMessagesInfo();
        Map<String, WordInfo> words = bayesFilter.getExportWordsInfo();

        exchanger.exportMessagesInfo(messagesInfo);
        exchanger.exportWordInfo(words);

        exchanger.destroy();
    }

    public String getMessagesStat() {
        return bayesFilter.getMessagesInfo().getSpamMessageCount() + "/" + bayesFilter.getMessagesInfo().getHamMessageCount();
    }

    public String getStat() {
        return String.format("%s\n%s\n", bayesFilter.toString(), getMessagesStat());
    }
}
