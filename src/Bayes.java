import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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



//  хранимая статистика об 1 слове
class WordInfo
{
  public WordInfo(String word, long spamCount, long totalCount)
  {
    Word = word;
    SpamCount = spamCount;
    TotalCount = totalCount;
  }
  
  public String Word;
  public long SpamCount;
  public long TotalCount;
  
  public double SpamProbability() { return (double)SpamCount / TotalCount; }
}


//  обучаемый классификатор

public class Bayes {

  Map<String, WordInfo> m_Words;
		
  
  public Bayes()
  {
    m_Words = new HashMap<String, WordInfo>();
  }
  
  
  Set<String> GetCleanWords(String phrase)
  {
    //  разбиваем фразу на слова с применением регулярного выражения, тут же удаляем дубликаты слова
    Set<String> rawWords = new HashSet<String>(Arrays.asList(phrase.split("\\w+")));
    
    //  удаляем короткие слова, в качестве аргумента указана функция отбора слов, записанная в виде лямбда-выражения
    //  (здесь эта функция должна вернуть boolean - удалять или нет)
    rawWords.removeIf(w -> w.length() <= 3);
    
  
    Set<String> cleanWords = new HashSet<String>(); 
    
    //  переводим все слова в нижний регистр, проводим стемминг  
    //  стемминг (выделяем основу каждого слова, убирая окончания и суффиксы)
    for(String w : rawWords)
    {
      String word = WordStemming.stem(w.toLowerCase()); //  DEBUG: если надо, поправить локаль на universal
      cleanWords.add(word);
    }    
    
    return cleanWords;
  }
  
  
  //  обучение
  public void Learn(String phrase, boolean isSpam)
  {
    //  каждое слово заносим в таблицу и/или меняем его счетчики
    int incSpam = isSpam ? 1 : 0;
    for(String w : GetCleanWords(phrase))
    {
      //  если слова еще нет в таблице, добавляем
      WordInfo wordInfo = m_Words.get(w);
      if(wordInfo == null)
      {
        wordInfo = new WordInfo(w, 0, 0);
        m_Words.put(w, wordInfo);
      }
      
      //  меняем его счетчики
      wordInfo.SpamCount += incSpam;
      wordInfo.TotalCount++;
    }
  }
  
  
  //  Экспорт
  public Map<String, WordInfo> ExportWordsInfo() { return m_Words; }
  
  //  Импорт
  public void ImportWordsInfo(Map<String, WordInfo> wordsInfo) { m_Words = wordsInfo; }
  
  //  проверка
  //  возвращает вероятность того, что фраза является спамом
  public double Verify(String phrase)
  {
    double spamWordsProduction = 1.0;
    double notSpamWordsProduction = 1.0;
    
    for(String w : GetCleanWords(phrase))
    {
      WordInfo word = m_Words.get(w);
      
      //  TODO:
      //    пока примем такой вариант: если этого слова не было раньше в выборке при обучении, то мы его просто пропускаем  
      
      if(word == null)
        continue;
      
      spamWordsProduction *= word.SpamProbability();
      notSpamWordsProduction *= (1.0 - word.SpamProbability());
    }
    
    return spamWordsProduction / (spamWordsProduction + notSpamWordsProduction);
  }
}









