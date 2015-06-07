package com.sleep.spamfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TBL{

    //private Map<String, WordInfo> mWords;
    //private MessagesInfo messagesInfo;

    public TBL() {
        //mWords = words;
        //this.messagesInfo = messagesInfo;
    }

    public List<String> extractTelephones(String phrase){
        //if (value == null) throw new NullArgumentException("urls to extract");
        List<String> result = new ArrayList<String>();
        String telephonePattern = "((8|\\+7)-?)?\\(?\\d{3,5}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}((-?\\d{1})?-?\\d{1})?";
        Pattern p = Pattern.compile(telephonePattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(phrase);
        String remove = "[\\+\\- \\( \\)]";
        while (m.find()) {

            result.add(phrase.substring(m.start(0), m.end(0)).replaceAll(remove,""));
        }
        return result;
    }

}