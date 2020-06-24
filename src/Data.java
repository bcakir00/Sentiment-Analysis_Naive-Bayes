import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Data {
    public void setupTrainingData(List<Status> tweets) {
        Scanner scanner = new Scanner(System.in);
        int tweetCounter = 0;

        System.out.println("\n Please classify the following tweets as 1:positive, 2:neutral, 3:negative, blank to " +
                "discard tweet, \"test\" to test, or \"exit\" to stop en delete training set.");

        for(Status tweet: tweets) {
            String tweetText = getText(tweet);
            System.out.println(tweetCounter+1 + "/" + tweets.size() + " - " + tweetText);

            //tweetText = cleanTweet(tweetText);
            //List<String> tokenizedTweetText = tokenizeTweet(tweetText);

            String input = scanner.nextLine();
            switch(input) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "test":
                    break;
                default:
                    break;
            }

            //increment total in mongo with one.
            tweetCounter++;
        }
    }

    private String getText(Status tweet) {
        String tweetText;

        if(tweet.isRetweet()){
            tweetText = tweet.getRetweetedStatus().getText();
        } else {
            tweetText = tweet.getText();
        }

        return tweetText;
    }

    private List<String> cleanTweet(String[] splitTweetText) {
        List<String> cleanedTokens = new ArrayList<>();

        for(String word : splitTweetText) {
            word = word.replaceAll("[.]", "");
            word = word.replaceAll(",", "");
            word = word.toLowerCase();

            if(Pattern.matches("[a-z[A-Z]]*", word)) {
                cleanedTokens.add(word);
            }
        }

        return cleanedTokens;
    }

    private String[] tokenizeTweet(String tweetText) {
        return tweetText.split("\\s+");
    }
}
