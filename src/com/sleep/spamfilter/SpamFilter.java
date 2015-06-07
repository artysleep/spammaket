package com.sleep.spamfilter;

import com.sleep.spamfilter.bayes.BayesFilter;
import com.sleep.spamfilter.bayes.MessagesInfo;
import com.sleep.spamfilter.bayes.WordInfo;
import com.sleep.spamfilter.blacklist.PhoneNumber;
import com.sleep.spamfilter.blacklist.TBL;
import com.sleep.spamfilter.urlblacklist.SURBL;
import com.sleep.spamfilter.urlblacklist.URLRecord;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpamFilter {
    private BayesFilter bayesFilter;
    private SURBL surbl;
    private TBL tbl;

    public SpamFilter() {
        Exchanger exchanger = new Exchanger();

        MessagesInfo messagesInfo = exchanger.importMessagesInfo();
        Map<String, WordInfo> words = exchanger.importWordInfo();
        Set<PhoneNumber> phoneNumbersBlackList = exchanger.importPhoneBlackList();
        Set<URLRecord> urlBlackList = exchanger.importURLBlackList();
        exchanger.destroy();

        this.bayesFilter = new BayesFilter(messagesInfo, words);
        this.tbl = new TBL(phoneNumbersBlackList);
        this.surbl = new SURBL(urlBlackList);
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
        surbl.learn(message, isSpam);
        bayesFilter.learn(message, isSpam);
        bayesFilter.getMessagesInfo().incCounter(1, isSpam);
    }

    public boolean verify(String phone, String message) {
        if (verifyTelephone(phone)){
            return true;
        }
        if (verifyUrls(message)){
            return true;
        }
        double spamProb = bayesFilter.verify(message);
        return spamProb > 0.5;
    }

    public List<String> surbl(String message) {
        //surbl.extractUrls(message);
        return surbl.extractUrls(message);
    }

    public void save() {
        Exchanger exchanger = new Exchanger();
        MessagesInfo messagesInfo = bayesFilter.getMessagesInfo();
        Map<String, WordInfo> words = bayesFilter.getExportWordsInfo();
        Set<PhoneNumber> phoneNumbersBlackList = tbl.getBlackList();
        Set<URLRecord> urlBlackList = surbl.getBlackList();

        exchanger.exportMessagesInfo(messagesInfo);
        exchanger.exportWordInfo(words);
        exchanger.exportPhoneBlackList(phoneNumbersBlackList);
        exchanger.exportURLBlackList(urlBlackList);

        exchanger.destroy();
    }

    public String getMessagesStat() {
        return bayesFilter.getMessagesInfo().getSpamMessageCount() + "/" + bayesFilter.getMessagesInfo().getHamMessageCount();
    }

    public String getStat() {
        return String.format("%s\n%s\n%s\n%s\n", bayesFilter.toString(), getMessagesStat(), tbl.toString(), surbl.toString());
    }

    public boolean verifyTelephone(String telephone) {
        return telephone != null && tbl.verify(telephone);
    }

    public boolean verifyUrls(String message) {
        return surbl.verify(message);
    }
}
