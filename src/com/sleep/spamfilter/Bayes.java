package com.sleep.spamfilter;

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

public class Bayes {
  private Map<String, WordInfo> mWords;

  public Bayes() {
    mWords = new HashMap<String, WordInfo>();
  }
  
  private Set<String> getCleanWords(String phrase) {
    //  ��������� ����� �� ����� � ����������� ����������� ���������, ��� �� ������� ��������� �����

    String regex = "[0-9-,!+\"-()/|.]";
    String updPhrase = phrase.replaceAll(regex, " ");

    Set<String> rawWords = new HashSet<String>(Arrays.asList(updPhrase.split(".\\s+")));
    
    //  ������� �������� �����, � �������� ��������� ������� ������� ������ ����, ���������� � ���� ������-���������
    //  (����� ��� ������� ������ ������� boolean - ������� ��� ���)
    rawWords.removeIf(w -> w.length() <= 3);

    Set<String> cleanWords = new HashSet<String>(); 
    
    //  ��������� ��� ����� � ������ �������, �������� ��������  
    //  �������� (�������� ������ ������� �����, ������ ��������� � ��������)
    for (String w : rawWords) {
      String word = WordStemming.stem(w); //  DEBUG: ���� ����, ��������� ������ �� universal
      cleanWords.add(word);
    }    
    
    return cleanWords;
  }

  //  ��������
  public void learn(List<String> phrases, boolean isSpam) {
    for (String phrase : phrases) {
      learn(phrase, isSpam);
    }
  }

  //  ��������
  public void learn(String phrase, boolean isSpam) {
    //  ������ ����� ������� � ������� �/��� ������ ��� ��������
    int incSpam = isSpam ? 1 : 0;

    for (String w : getCleanWords(phrase)) {
      //  ���� ����� ��� ��� � �������, ���������
      WordInfo wordInfo = mWords.get(w);
      if (wordInfo == null) {
        wordInfo = new WordInfo(w);
        mWords.put(w, wordInfo);
      }

      //  ������ ��� ��������
      wordInfo.spamCount += incSpam;
      wordInfo.totalCount++;
    }
  }
  
  
  //  �������
  public Map<String, WordInfo> getExportWordsInfo() {
    return mWords;
  }
  
  //  ������
  public void setImportWordsInfo(Map<String, WordInfo> wordsInfo) {
    mWords = wordsInfo;
  }
  
  //  ��������
  //  ���������� ����������� ����, ��� ����� �������� ������
  public double verify(String phrase) {
    double spamWordsProduction = 1.0;
    double notSpamWordsProduction = 1.0;
    
    for (String w : getCleanWords(phrase)) {
      WordInfo word = mWords.get(w);
      
      //  TODO:
      //    ���� ������ ����� �������: ���� ����� ����� �� ���� ������ � ������� ��� ��������, �� �� ��� ������ ����������  
      if (word != null) {
        spamWordsProduction *= word.SpamProbability();
        notSpamWordsProduction *= (1.0 - word.SpamProbability());
      }
    }
    
    return spamWordsProduction / (spamWordsProduction + notSpamWordsProduction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (WordInfo word : mWords.values()){
      sb.append(word.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
}









