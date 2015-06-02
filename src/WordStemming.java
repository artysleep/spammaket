import java.util.regex.Matcher;
import java.util.regex.Pattern;



//  �������� �������
public class WordStemming {

  private static final Pattern PERFECTIVEGROUND = Pattern.compile("((��|����|������|��|����|������)|((?&lt;=[��])(�|���|�����)))$");  
  
  private static final Pattern REFLEXIVE = Pattern.compile("(�[��])$");  

  private static final Pattern ADJECTIVE = Pattern.compile("(��|��|��|��|���|���|��|��|��|��|��|��|��|��|���|���|���|���|��|��|��|��|��|��|��|��)$");  

  private static final Pattern PARTICIPLE = Pattern.compile("((���|���|���)|((?<=[��])(��|��|��|��|�)))$");  

  private static final Pattern VERB = Pattern.compile("((���|���|���|����|����|���|���|���|��|��|��|��|��|��|��|���|���|���|��|���|���|��|��|���|���|���|���|��|�)|((?<=[��])(��|��|���|���|��|�|�|��|�|��|��|��|��|��|��|���|���)))$");  

  private static final Pattern NOUN = Pattern.compile("(�|��|��|��|��|�|����|���|���|��|��|�|���|��|��|��|�|���|��|���|��|��|��|�|�|��|���|��|�|�|��|��|�|��|��|�)$");  

  private static final Pattern RVRE = Pattern.compile("^(.*?[���������])(.*)$");  

  private static final Pattern DERIVATIONAL = Pattern.compile(".*[^���������]+[���������].*����?$");  

  private static final Pattern DER = Pattern.compile("����?$");  

  private static final Pattern SUPERLATIVE = Pattern.compile("(����|���)$");  

  private static final Pattern I = Pattern.compile("�$");  
  private static final Pattern P = Pattern.compile("�$");  
  private static final Pattern NN = Pattern.compile("��$");  

  public static String stem(String word) 
  {  
      word = word.toLowerCase();  
      word = word.replace('�', '�');
      
      Matcher m = RVRE.matcher(word);  
      if (m.matches()) {  
          String pre = m.group(1);  
          String rv = m.group(2);  
          String temp = PERFECTIVEGROUND.matcher(rv).replaceFirst("");  
          if (temp.equals(rv)) {  
              rv = REFLEXIVE.matcher(rv).replaceFirst("");  
              temp = ADJECTIVE.matcher(rv).replaceFirst("");  
              if (!temp.equals(rv)) {  
                  rv = temp;  
                  rv = PARTICIPLE.matcher(rv).replaceFirst("");  
              } else {  
                  temp = VERB.matcher(rv).replaceFirst("");  
                  if (temp.equals(rv)) {  
                      rv = NOUN.matcher(rv).replaceFirst("");  
                  } else {  
                      rv = temp;  
                  }  
              }  

          } else {  
              rv = temp;  
          }  

          rv = I.matcher(rv).replaceFirst("");  

          if (DERIVATIONAL.matcher(rv).matches()) {  
              rv = DER.matcher(rv).replaceFirst("");  
          }  

          temp = P.matcher(rv).replaceFirst("");  
          if (temp.equals(rv)) {  
              rv = SUPERLATIVE.matcher(rv).replaceFirst("");  
              rv = NN.matcher(rv).replaceFirst("�");  
          }else{  
              rv = temp;  
          }  
          word = pre + rv;  

      }  

      return word;  
  }  

}  