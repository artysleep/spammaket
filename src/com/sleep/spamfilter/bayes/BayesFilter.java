package com.sleep.spamfilter.bayes;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.russianStemmer;

import java.util.*;


//	��������:
//		�� �����:
//			- ������ ����
//    �� ������:
//      - ����� ���� � ������������� ���������

//	��� ������ �����
//	�� �����:
//		- �����
//		- �������� �� ����� ������ (true/false) 

//	������ ������� ���� �������� � ��������� Bayes-�������.
//	(���� �������� �����, ����������� � ����, ��� ��� ����, �� ��� ????)  

//	����������:
//		��������.
//		������� ����������, ���������� ��� ��������.
//		������.
//		�������� ���� �� ����.


//  ��������� �������������
public class BayesFilter {
  private Map<String, WordInfo> mWords;
  private MessagesInfo messagesInfo;

  public BayesFilter(MessagesInfo messagesInfo, Map<String, WordInfo> words) {
    mWords = words;
    this.messagesInfo = messagesInfo;
  }

  public BayesFilter() {
    this(new MessagesInfo(), new HashMap<String, WordInfo>());
  }

  private Set<String> getCleanWords(String phrase) {
    //  ��������� ����� �� ����� � ����������� ����������� ���������, ��� �� ������� ��������� �����

    String regexRemove = "[0-9,!\\+\"\\-\\(\\)/\\|\\.@#\\$%\\^\\&\\*\\?\\<\\>\\{\\}\\[\\]\\\\]";
    String updPhrase = phrase.replaceAll(regexRemove, " ");
    updPhrase = updPhrase.toLowerCase();
    Set<String> rawWords = new HashSet<String>(Arrays.asList(updPhrase.split(".\\s+")));

    //  ������� �������� �����, � �������� ��������� ������� ������� ������ ����, ���������� � ���� ������-���������
    //  (����� ��� ������� ������ ������� boolean - ������� ��� ���)
    rawWords.removeIf(w -> w.length() <= 3);

    Set<String> cleanWords = new HashSet<String>();
    //  ��������� ��� ����� � ������ �������, �������� ��������  
    //  �������� (�������� ������ ������� �����, ������ ��������� � ��������)
    SnowballStemmer stemmer = new russianStemmer();
    for (String w : rawWords) {
      stemmer.setCurrent(w);
      stemmer.stem();
      String word = stemmer.getCurrent(); //  DEBUG: ���� ����, ��������� ������ �� universal
      cleanWords.add(word);
    }    
    
    return cleanWords;
  }

  //  ��������
  public void learn(String phrase, boolean isSpam) {
    //  ������ ����� ������� � ������� �/��� ������ ��� ��������
    /*int incSpam = isSpam ? 1 : 0;

    for (String w : getCleanWords(phrase)) {
      //  ���� ����� ��� ��� � �������, ���������
      WordInfo wordInfo = mWords.get(w);
      if (wordInfo == null) {
        wordInfo = new WordInfo(w);
        mWords.put(w, wordInfo);
      }

      //  ������ ��� ��������
      wordInfo.spamCount += incSpam;
      //wordInfo.totalCount++;
    }*/
    if (isSpam) {
      for (String w : getCleanWords(phrase)) {
        //  ���� ����� ��� ��� � �������, ���������
        WordInfo wordInfo = mWords.get(w);
        if (wordInfo == null) {
          wordInfo = new WordInfo(w);
          mWords.put(w, wordInfo);
          }
        wordInfo.spamCount++;
      }
    } else {
        for (String w : getCleanWords(phrase)) {
          //  ���� ����� ��� ��� � �������, ���������
          WordInfo wordInfo = mWords.get(w);
          if (wordInfo == null) {
            wordInfo = new WordInfo(w);
            mWords.put(w, wordInfo);
          }
          wordInfo.hamCount++;
      }
    }
  }
  
  
  //  �������
  public Map<String, WordInfo> getExportWordsInfo() {
    return mWords;
  }
  public MessagesInfo getMessagesInfo() {
    return messagesInfo;
  }


  //  ��������
  //  ���������� ����������� ����, ��� ����� �������� ������
  public double verify(String phrase) {
    double spamWordsProduction = 1.0;
    double hamWordsProduction = 1.0;
    
    for (String w : getCleanWords(phrase)) {
      WordInfo word = mWords.get(w);
      
      //  TODO:
      //    ���� ������ ����� �������: ���� ����� ����� �� ���� ������ � ������� ��� ��������, �� �� ��� ������ ����������  
      if (word != null) {
        spamWordsProduction = word.spamCount/ messagesInfo.getSpamMessageCount();
        hamWordsProduction = word.hamCount/ messagesInfo.getHamMessageCount();

        /*spamWordsProduction *= word.SpamProbability();
        notSpamWordsProduction *= (1.0 - word.SpamProbability());*/
      }
    }
    
    return spamWordsProduction / (spamWordsProduction + hamWordsProduction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (WordInfo word : mWords.values()){
      sb.append(word.toString());
      sb.append(" "+WordInfo.spamProbability(word, messagesInfo));
      sb.append("\n");
    }
    return sb.toString();
  }
}









