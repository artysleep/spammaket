package com.sleep.spamfilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


//	обучение:
//		на входе:
//			- список фраз
//    на выходе:
//      - набор слов с коэффициентом спамности

//	для каждой фразы
//	на входе:
//		- фраза
//		- является ли фраза спамом (true/false) 

//	Список спамных слов хранится в обучаемом Bayes-объекте.
//	(Если приходят слова, совпадающие с теми, что уже были, то что ????)  

//	Функционал:
//		Обучение.
//		Экспорт информации, полученной при обучении.
//		Импорт.
//		Проверка фраз на спам.


//  обучаемый классификатор

public class Bayes {
  private Map<String, WordInfo> mWords;

  public Bayes() {
    mWords = new HashMap<String, WordInfo>();
  }
  
  private Set<String> getCleanWords(String phrase) {
    //  разбиваем фразу на слова с применением регулярного выражения, тут же удаляем дубликаты слова
    Set<String> rawWords = new HashSet<String>(Arrays.asList(phrase.split("\\w+")));
    
    //  удаляем короткие слова, в качестве аргумента указана функция отбора слов, записанная в виде лямбда-выражения
    //  (здесь эта функция должна вернуть boolean - удалять или нет)
    rawWords.removeIf(w -> w.length() <= 3);

    Set<String> cleanWords = new HashSet<String>(); 
    
    //  переводим все слова в нижний регистр, проводим стемминг  
    //  стемминг (выделяем основу каждого слова, убирая окончания и суффиксы)
    for (String w : rawWords) {
      String word = WordStemming.stem(w); //  DEBUG: если надо, поправить локаль на universal
      cleanWords.add(word);
    }    
    
    return cleanWords;
  }

  //  обучение
  public void learn(String phrase, boolean isSpam) {
    //  каждое слово заносим в таблицу и/или меняем его счетчики
    int incSpam = isSpam ? 1 : 0;

    for (String w : getCleanWords(phrase)) {
      //  если слова еще нет в таблице, добавляем
      WordInfo wordInfo = mWords.get(w);
      if (wordInfo == null) {
        wordInfo = new WordInfo(w);
        mWords.put(w, wordInfo);
      }
      
      //  меняем его счетчики
      wordInfo.spamCount += incSpam;
      wordInfo.totalCount++;
    }
  }
  
  
  //  Экспорт
  public Map<String, WordInfo> getExportWordsInfo() {
    return mWords;
  }
  
  //  Импорт
  public void setImportWordsInfo(Map<String, WordInfo> wordsInfo) {
    mWords = wordsInfo;
  }
  
  //  проверка
  //  возвращает вероятность того, что фраза является спамом
  public double verify(String phrase) {
    double spamWordsProduction = 1.0;
    double notSpamWordsProduction = 1.0;
    
    for (String w : getCleanWords(phrase)) {
      WordInfo word = mWords.get(w);
      
      //  TODO:
      //    пока примем такой вариант: если этого слова не было раньше в выборке при обучении, то мы его просто пропускаем  
      if (word != null) {
        spamWordsProduction *= word.SpamProbability();
        notSpamWordsProduction *= (1.0 - word.SpamProbability());
      }
    }
    
    return spamWordsProduction / (spamWordsProduction + notSpamWordsProduction);
  }
}









