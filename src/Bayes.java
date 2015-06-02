import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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



//  �������� ���������� �� 1 �����
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


//  ��������� �������������

public class Bayes {

  Map<String, WordInfo> m_Words;
		
  
  public Bayes()
  {
    m_Words = new HashMap<String, WordInfo>();
  }
  
  
  Set<String> GetCleanWords(String phrase)
  {
    //  ��������� ����� �� ����� � ����������� ����������� ���������, ��� �� ������� ��������� �����
    Set<String> rawWords = new HashSet<String>(Arrays.asList(phrase.split("\\w+")));
    
    //  ������� �������� �����, � �������� ��������� ������� ������� ������ ����, ���������� � ���� ������-���������
    //  (����� ��� ������� ������ ������� boolean - ������� ��� ���)
    rawWords.removeIf(w -> w.length() <= 3);
    
  
    Set<String> cleanWords = new HashSet<String>(); 
    
    //  ��������� ��� ����� � ������ �������, �������� ��������  
    //  �������� (�������� ������ ������� �����, ������ ��������� � ��������)
    for(String w : rawWords)
    {
      String word = WordStemming.stem(w.toLowerCase()); //  DEBUG: ���� ����, ��������� ������ �� universal
      cleanWords.add(word);
    }    
    
    return cleanWords;
  }
  
  
  //  ��������
  public void Learn(String phrase, boolean isSpam)
  {
    //  ������ ����� ������� � ������� �/��� ������ ��� ��������
    int incSpam = isSpam ? 1 : 0;
    for(String w : GetCleanWords(phrase))
    {
      //  ���� ����� ��� ��� � �������, ���������
      WordInfo wordInfo = m_Words.get(w);
      if(wordInfo == null)
      {
        wordInfo = new WordInfo(w, 0, 0);
        m_Words.put(w, wordInfo);
      }
      
      //  ������ ��� ��������
      wordInfo.SpamCount += incSpam;
      wordInfo.TotalCount++;
    }
  }
  
  
  //  �������
  public Map<String, WordInfo> ExportWordsInfo() { return m_Words; }
  
  //  ������
  public void ImportWordsInfo(Map<String, WordInfo> wordsInfo) { m_Words = wordsInfo; }
  
  //  ��������
  //  ���������� ����������� ����, ��� ����� �������� ������
  public double Verify(String phrase)
  {
    double spamWordsProduction = 1.0;
    double notSpamWordsProduction = 1.0;
    
    for(String w : GetCleanWords(phrase))
    {
      WordInfo word = m_Words.get(w);
      
      //  TODO:
      //    ���� ������ ����� �������: ���� ����� ����� �� ���� ������ � ������� ��� ��������, �� �� ��� ������ ����������  
      
      if(word == null)
        continue;
      
      spamWordsProduction *= word.SpamProbability();
      notSpamWordsProduction *= (1.0 - word.SpamProbability());
    }
    
    return spamWordsProduction / (spamWordsProduction + notSpamWordsProduction);
  }
}









