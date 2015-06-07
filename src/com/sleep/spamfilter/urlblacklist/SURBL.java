package com.sleep.spamfilter.urlblacklist;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SURBL{
    private Set<URLRecord> blacklist;

    public SURBL() {
        this (new HashSet<URLRecord>());
    }
    public SURBL(Set<URLRecord> blacklist) {
        this.blacklist = blacklist;
    }

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

    public boolean verify(String phrase) {
        List<String> urls = extractUrls(phrase);
        for (String url : urls) {
            return verify(new URLRecord(url));
        }
        return false;
    }

    public boolean verify(URLRecord url){
        for (URLRecord urlBL : blacklist) {
            if (urlBL.getURL().equals(url.getURL())) {
                return true;
            }
        }
        return false;
    }

    public void learn (String phrase, boolean isSpam){
        List<String> urls = extractUrls(phrase);
        for (String url : urls) {
            URLRecord urlRecord = new URLRecord(url);
            learn(urlRecord, isSpam);
        }
    }
    public void learn (URLRecord url, boolean isSpam){
        if (isSpam) {
            for (URLRecord urlBL : blacklist) {
                if (urlBL.getURL().equals(url.getURL())) {
                    return;
                }
            }
            blacklist.add(url);
        }
    }

    public Set<URLRecord> getBlackList() {
        return blacklist;
    }

    @Override
    public String toString() {
        return blacklist.toString();
    }
}