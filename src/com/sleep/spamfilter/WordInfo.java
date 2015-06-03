package com.sleep.spamfilter;

//  хранимая статистика об 1 слове
public class WordInfo {
  public String word;
  public long spamCount;
  public long totalCount;

  public WordInfo(String word) {
    this(word, 0, 0);
  }

  public WordInfo(String word, long spamCount, long totalCount) {
    this.word = word;
    this.spamCount = spamCount;
    this.totalCount = totalCount;
  }

  public double SpamProbability() {
    if (totalCount > 0) {
      return (double) spamCount / totalCount;
    } else {
      return 0;
    }
  }

  @Override
  public String toString() {
    return word + "-" + SpamProbability();
  }
}
