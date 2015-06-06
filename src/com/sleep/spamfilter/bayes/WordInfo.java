package com.sleep.spamfilter.bayes;

import javax.persistence.*;

//  хранимая статистика об 1 слове
@Entity
@Table(name = "WordInfo")
public class WordInfo {
  @Id
  @Column(name= "word")
  public String word;
  @Column(name= "spamCount")
  public long spamCount;
  @Column(name= "hamCount")
  public long hamCount;

  public WordInfo() {
    this("", 0, 0);
  }

  public WordInfo(String word) {
    this(word, 0, 0);
  }

  public WordInfo(String word, long spamCount, long hamCount) {
    this.word = word;
    this.spamCount = spamCount;
    this.hamCount = hamCount;
  }

  public static double spamProbability(WordInfo word, MessagesInfo messagesInfo) {
    return spamProbability(word, messagesInfo.getHamMessageCount(), messagesInfo.getSpamMessageCount());
  }

  public static double spamProbability(WordInfo word, long hamMessageCount, long spamMessageCount) {
    if ((hamMessageCount > 0)&&(spamMessageCount > 0)){
      double a =(double) word.spamCount / (double) spamMessageCount;
      double b = (double) word.hamCount / (double) hamMessageCount;
      return a / (b + a);
    }   else {
      return 0;
    }
  }

  public static double hamProbability(WordInfo word, MessagesInfo messagesInfo) {
    return hamProbability(word, messagesInfo.getHamMessageCount(), messagesInfo.getSpamMessageCount());
  }

  public static double hamProbability(WordInfo word, long hamMessageCount, long spamMessageCount) {
    if ((hamMessageCount > 0)&&(spamMessageCount > 0)){
      double a =(double) word.spamCount / (double) spamMessageCount;
      double b = (double) word.hamCount / (double) hamMessageCount;
      return b / (a + b);
    }   else {
      return 0;
    }
  }

  @Override
  public String toString() {
    return String.format("%4d  %4d %s", spamCount, hamCount, word);
  }
}
