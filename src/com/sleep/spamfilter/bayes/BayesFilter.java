package com.sleep.spamfilter.bayes;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.russianStemmer;

import java.util.*;


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
    //  разбиваем фразу на слова с применением регулярного выражения, тут же удаляем дубликаты слова

    String regexRemove = "[0-9,!\\+\"\\-\\(\\)/\\|\\.@#\\$%\\^\\&\\*\\?\\<\\>\\{\\}\\[\\]\\\\]";
    String updPhrase = phrase.replaceAll(regexRemove, " ");
    updPhrase = updPhrase.toLowerCase();
    Set<String> rawWords = new HashSet<String>(Arrays.asList(updPhrase.split(".\\s+")));

    //  удаляем короткие слова, в качестве аргумента указана функция отбора слов, записанная в виде лямбда-выражения
    //  (здесь эта функция должна вернуть boolean - удалять или нет)
    rawWords.removeIf(w -> w.length() <= 2);

    Set<String> cleanWords = new HashSet<String>();
    //  переводим все слова в нижний регистр, проводим стемминг  
    //  стемминг (выделяем основу каждого слова, убирая окончания и суффиксы)
    SnowballStemmer stemmer = new russianStemmer();
    for (String w : rawWords) {
      stemmer.setCurrent(w);
      stemmer.stem();
      String word = stemmer.getCurrent(); //  DEBUG: если надо, поправить локаль на universal
      cleanWords.add(word);
    }    
    
    return cleanWords;
  }

  //  обучение
  public void learn(String phrase, boolean isSpam) {
    //  каждое слово заносим в таблицу и/или меняем его счетчики
    /*int incSpam = isSpam ? 1 : 0;

    for (String w : getCleanWords(phrase)) {
      //  если слова еще нет в таблице, добавляем
      WordInfo wordInfo = mWords.get(w);
      if (wordInfo == null) {
        wordInfo = new WordInfo(w);
        mWords.put(w, wordInfo);
      }

      //  меняем его счетчики
      wordInfo.spamCount += incSpam;
      //wordInfo.totalCount++;
    }*/
    if (isSpam) {
      for (String w : getCleanWords(phrase)) {
        //  если слова еще нет в таблице, добавляем
        WordInfo wordInfo = mWords.get(w);
        if (wordInfo == null) {
          wordInfo = new WordInfo(w);
          mWords.put(w, wordInfo);
          }
        wordInfo.spamCount++;
      }
    } else {
        for (String w : getCleanWords(phrase)) {
          //  если слова еще нет в таблице, добавляем
          WordInfo wordInfo = mWords.get(w);
          if (wordInfo == null) {
            wordInfo = new WordInfo(w);
            mWords.put(w, wordInfo);
          }
          wordInfo.hamCount++;
      }
    }
  }
  
  
  //  Экспорт
  public Map<String, WordInfo> getExportWordsInfo() {
    return mWords;
  }
  public MessagesInfo getMessagesInfo() {
    return messagesInfo;
  }


  //  проверка
  //  возвращает вероятность того, что фраза является спамом
  public double verify(String phrase) {
    double spamWordsProduction = 0;
    double hamWordsProduction = 0;
    double wordSpamOrHam = 0.0;
    double result = 1.0;
    /*int laplaceFactor = 1;
    int total = 0;*/

    if (phrase.length()<=10) {result = 0;}


    for (String w : getCleanWords(phrase)) {
      WordInfo word = mWords.get(w);
      //  TODO:
      //   пока примем такой вариант: если этого слова не было раньше в выборке при обучении, то мы его просто пропускаем

      if (word != null) {

        spamWordsProduction = (double) word.spamCount/ (double) messagesInfo.getSpamMessageCount();
        hamWordsProduction = (double) word.hamCount/ (double) messagesInfo.getHamMessageCount();
        wordSpamOrHam = spamWordsProduction/(spamWordsProduction+hamWordsProduction);

        result = result*wordSpamOrHam;
      }
    }
    //System.out.println(wordSpamProbability);
    return result;

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









