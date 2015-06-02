package com.sleep.spamfilter;

//  хранимая статистика об 1 слове
public class WordInfo {
  public String Word;
  public long   SpamCount;
  public long totalCount;

  public WordInfo(String word) {
    this(word, 0, 0);
  }

  public WordInfo(String word, long spamCount, long totalCount) {
    Word = word;
    SpamCount = spamCount;
    this.totalCount = totalCount;
  }

  public double SpamProbability() {
    if (totalCount > 0) {
      return (double) SpamCount / totalCount;
    } else {
      return 0;
    }
  }
}
