package com.poc.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 
 *Service having actual logic of calculation of top 10 hashtags words from twitter (file). 
 *for simplicity I am taking file assuming thats contains all the tweets from twitter
 */
public class TwitterService {
  
  private static final String SPACE = " ";
  private static final char CHAR_HASH = '#';
  private static final String TWITTER_INPUT_PATH = "src/resources/twitter-input.txt";
  
  public List<String> calculateTop10HashTagWords() {
    
    // input file that contains all the tweets from twitter along with hashtags i.e. #sachine
    Path textFilePath = Paths.get(TWITTER_INPUT_PATH);
    try {
      List<String> words = getAllWordsFromFile(textFilePath); // word
      Stream<String> streamOfHashTagWord = getHashTagWord(words); // hashTagWord
      Map<String, Integer> hashTagWordMapwithFrequency =
          getAllHashTagWordMapwithFrequency(streamOfHashTagWord);//map of hashtag word with frequency

      return getTopTenHashTagList(hashTagWordMapwithFrequency); //list of words
    } catch (IOException exception) {
      System.out.print("Exception while processing tweets");
      exception.printStackTrace();
    }
    return null;
  }
  
  /**
   * reads the file and returns all the words;
   */
  private List<String> getAllWordsFromFile(Path textFilePath) throws IOException  {

      Stream<String> lines= Files.lines(textFilePath, Charset.defaultCharset()); //line
      return lines
          .flatMap(line -> Arrays.stream(line.split(SPACE)))
          .collect(Collectors.toList()); //word
  }
  
  
  //filters hashtag words
  private Stream<String> getHashTagWord(List<String> words) {
    return words
        .stream()
        .filter(word -> isHashTagWord(word));
  }


  private static boolean isHashTagWord(String word) {
    return word.length()>0 && word.charAt(0) == CHAR_HASH;
  }


  private Map<String, Integer> getAllHashTagWordMapwithFrequency(
      Stream<String> streamOfHashTagWord) {

    return streamOfHashTagWord
        .collect(Collectors
            .toMap(word -> word.toLowerCase(), word -> 1, Integer::sum));
  }
  
  /**
   * calculating list of top 10 hashtag words
   * from map of string with frequency
   */
  private  List<String> getTopTenHashTagList(
      Map<String, Integer> hashTagWordMapwithFrequency) {
    
    Function<Map.Entry<String,Integer>,String> fun=entry -> entry.getKey();

    return hashTagWordMapwithFrequency.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) // sorting from max frequency to min frequency words
        .limit(10)                                                      //taking top 10
        //.map(entry -> entry.getKey()) 
        .map(fun)
        .collect(Collectors.toList());
  }
}
