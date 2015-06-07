package com.sleep.spamfilter;



import jdk.nashorn.internal.runtime.regexp.RegExp;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.russianStemmer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SURBL{

    //private Map<String, WordInfo> mWords;
    //private MessagesInfo messagesInfo;

    public SURBL() {
        //mWords = words;
        //this.messagesInfo = messagesInfo;
    }
/*
    public SURBL() {

        //this(new MessagesInfo(), new HashMap<String, WordInfo>());
    }
*/

    public List<String> extractUrls(String phrase){
        //if (value == null) throw new NullArgumentException("urls to extract");
        List<String> result = new ArrayList<String>();
        String urlPattern = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(phrase);
        while (m.find()) {
            result.add(phrase.substring(m.start(0),m.end(0)));
        }
        return result;
    }

}