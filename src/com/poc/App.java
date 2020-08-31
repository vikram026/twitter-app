package com.poc;

import java.util.List;
import com.poc.service.TwitterService;

/**
 * 
 * This app reads all the tweets from the file and applies business logic to calculate top 10 hash
 * tags from these tweets. It stores the top 10 hashtags in the list. To make the things I used java
 * 8 steam API. Here I am printing the result in console and reading the twitter input from text
 * file.
 *
 */
public class App {

  private TwitterService twitterService;

  public static void main(String[] args) {
    App app=new App();
    app.printTop10HashTagWords();
  }

  private void printTop10HashTagWords() {
    
    twitterService= new TwitterService();
  //business logic to calculate top 10 hashTag words from all the tweets from twitter(file).
    List<String> topTenHashTagWordList = twitterService.calculateTop10HashTagWords(); 
 
    System.out.println("================= *********Result***********================");
    System.out
        .println("================= List of top 10 hashtag word =================\n");
    if(topTenHashTagWordList!=null)
      System.out.println(topTenHashTagWordList);
    else
      System.out.println("Something went wrong");
    
  }
}
