package com.sleep.spamfilter;

import com.sleep.spamfilter.bayes.BayesFilter;
import com.sleep.spamfilter.bayes.MessagesInfo;
import com.sleep.spamfilter.bayes.WordInfo;

import java.util.List;
import java.util.Map;

public class SpamFilter {
    private BayesFilter bayesFilter;
    private SURBL surbl;
    private TBL tbl;

    public SpamFilter() {
        Exchanger exchanger = new Exchanger();

        MessagesInfo messagesInfo = exchanger.importMessagesInfo();
        Map<String, WordInfo> words = exchanger.importWordInfo();
        exchanger.destroy();

        this.bayesFilter = new BayesFilter(messagesInfo, words);
        this.tbl = new TBL();
        this.surbl = new SURBL();
    }

    public void learn(List<String> messages, boolean isSpam) {

        for (String phrase : messages) {
            int delPos = phrase.indexOf(":");
            String phone = phrase.substring(0, delPos);
            String message = phrase.substring(delPos+1);
            learn(phone, message, isSpam);
        }
    }

    public void learn(String phone, String message, boolean isSpam) {
        if (phone!=null){
            tbl.learn(phone, isSpam);
        }
        bayesFilter.learn(message, isSpam);
        bayesFilter.getMessagesInfo().incCounter(1, isSpam);
    }

    public boolean verify(String phone, String message) {
        if (verifyTelephone(phone)){
            return true;
        }
        double spamProb = bayesFilter.verify(message);
        return spamProb>0.5;
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
        return String.format("%s\n%s\n%s\n", bayesFilter.toString(), getMessagesStat(), tbl.toString());
    }

    public boolean verifyTelephone(String telephone) {
        return telephone != null && tbl.verify(telephone);
    }
}
