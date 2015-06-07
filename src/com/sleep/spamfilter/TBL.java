package com.sleep.spamfilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TBL{

    private Set<Long> blackList;

    public TBL() {
        blackList = new HashSet<Long>();

    }

    public Long extractNumber(String str){
        String telephonePattern = "((8|\\+7)-?)?\\(?\\d{3,5}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}((-?\\d{1})?-?\\d{1})?";
        Pattern p = Pattern.compile(telephonePattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        String remove = "[\\+\\- \\( \\)]";
        if (m.find()){
            String phoneStr = str.substring(m.start(0), m.end(0)).replaceAll(remove, "");
            return Long.parseLong(phoneStr);
        }
        return null;
    }
    public void importPhoneBL(HashSet<Long> phones){
        blackList = phones;
    }

    public boolean verify(String phone) {
        Long number = extractNumber(phone);
        return number != null && verify(number);
    }
    public boolean verify(long phone){
        return blackList.contains(phone);
    }

    public void learn (String phone, boolean isSpam){
        Long number = extractNumber(phone);
        if (number!=null) {
            learn(number, isSpam);
        }
    }
    public void learn (long phone, boolean isSpam){
        if (isSpam) {
            blackList.add(phone);
        }
    }

    @Override
    public String toString() {
        return blackList.toString();
    }
}