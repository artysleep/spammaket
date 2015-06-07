package com.sleep.spamfilter.blacklist;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TBL{
    private Set<PhoneNumber> blackList;

    public TBL(Set<PhoneNumber> blackList) {
        this.blackList = blackList;
    }

    public PhoneNumber extractNumber(String str){
        String telephonePattern = "((8|\\+7)-?)?\\(?\\d{3,5}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}((-?\\d{1})?-?\\d{1})?";
        Pattern p = Pattern.compile(telephonePattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        String remove = "[\\+\\- \\( \\)]";
        if (m.find()){
            String phoneStr = str.substring(m.start(0), m.end(0)).replaceAll(remove, "");
            return new PhoneNumber(Long.parseLong(phoneStr));
        }
        return null;
    }

    public boolean verify(String phone) {
        PhoneNumber number = extractNumber(phone);
        return number != null && verify(number);
    }

    public boolean verify(long phone){
        for (PhoneNumber pn : blackList) {
            if (pn.getNumber() == phone) {
                return true;
            }
        }
        return false;
    }

    public boolean verify(PhoneNumber phone){
        for (PhoneNumber pn : blackList) {
            if (pn.getNumber() == phone.getNumber()) {
                return true;
            }
        }
        return false;
    }

    public void learn (String phone, boolean isSpam){
        PhoneNumber number = extractNumber(phone);
        if (number!=null) {
            learn(number, isSpam);
        }
    }
    public void learn (PhoneNumber phone, boolean isSpam){
        if (isSpam) {
            blackList.add(phone);
        }
    }

    public Set<PhoneNumber> getBlackList() {
        return blackList;
    }

    @Override
    public String toString() {
        return blackList.toString();
    }
}