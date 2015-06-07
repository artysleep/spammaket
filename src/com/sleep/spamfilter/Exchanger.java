package com.sleep.spamfilter;

import com.sleep.spamfilter.bayes.MessagesInfo;
import com.sleep.spamfilter.bayes.WordInfo;
import com.sleep.spamfilter.blacklist.PhoneNumber;
import com.sleep.spamfilter.urlblacklist.URLRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;

public class Exchanger {
    Session session;

    public Exchanger(){
        SessionFactory sessionFactory = new Configuration()
                .configure("/resources/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public MessagesInfo importMessagesInfo() {
        session.beginTransaction();
        MessagesInfo messagesInfo = (MessagesInfo) session.get(MessagesInfo.class, 0);
        if (messagesInfo == null){
            messagesInfo = new MessagesInfo();
        }
        System.out.println(messagesInfo);
        session.getTransaction().commit();
        return messagesInfo;
    }

    public void exportMessagesInfo(MessagesInfo messagesInfo) {
        session.beginTransaction();
        session.saveOrUpdate(messagesInfo);
        System.out.println(messagesInfo);
        session.getTransaction().commit();
    }

    public Map<String, WordInfo> importWordInfo() {
        session.beginTransaction();

        List<WordInfo> words = session.createCriteria(WordInfo.class).list();
        Map<String, WordInfo> result = new HashMap<>();
        for (WordInfo wordInfo : words) {
            result.put(wordInfo.word, wordInfo);
        }
        session.getTransaction().commit();
        return result;
    }

    public void exportWordInfo(Map<String, WordInfo> wordInfos) {
        session.beginTransaction();
        for (String key : wordInfos.keySet()) {
            WordInfo wordInfo = wordInfos.get(key);
            session.saveOrUpdate(wordInfo);
        }
        session.getTransaction().commit();
    }

    public Set<PhoneNumber> importPhoneBlackList() {
        session.beginTransaction();
        List<PhoneNumber> numbers = session.createCriteria(PhoneNumber.class).list();
        session.getTransaction().commit();
        return new HashSet<>(numbers);
    }

    public void exportPhoneBlackList(Set<PhoneNumber> numbers) {
        session.beginTransaction();
        for (PhoneNumber number : numbers) {
            session.saveOrUpdate(number);
        }
        session.getTransaction().commit();
    }

    public Set<URLRecord> importURLBlackList() {
        session.beginTransaction();
        List<URLRecord> urls = session.createCriteria(URLRecord.class).list();
        session.getTransaction().commit();
        return new HashSet<>(urls);
    }

    public void exportURLBlackList(Set<URLRecord> urls) {
        session.beginTransaction();
        for (URLRecord url : urls) {
            session.saveOrUpdate(url);
        }
        session.getTransaction().commit();
    }

    public void destroy(){
        session.close();
    }
}
