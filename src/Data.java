import org.bson.Document;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Data {
    public List<List<Status>> splitTrainingData(List<Status> tweets) {
        List<List<Status>> set = new ArrayList<>();
        List<Status> trainingSet = new ArrayList<>();
        List<Status> testingSet = new ArrayList<>();
        int tweetSize = tweets.size();
        int tweetCounter = 0;

        for(Status tweet : tweets) {
            if(tweetCounter < tweetSize * 0.8) {
                trainingSet.add(tweet);
            } else {
                testingSet.add(tweet);
            }

            tweetCounter++;
        }

        set.add(trainingSet);
        set.add(testingSet);

        return set;
    }

    public void setupTrainingData(String collectionName, List<List<Status>> set) {
        Database database =  new Database("IPASS");
        Scanner scanner = new Scanner(System.in);
        int tweetCounter = 1;
        List<Status> trainingSet = set.get(0);

        for(Status tweet: trainingSet) {
            String tweetText = getText(tweet);
            String[] tokenizedTweet = tokenizeTweet(tweetText);
            List<String> cleanTokenizedTweet = cleanTweet(tokenizedTweet);

            System.out.println(tweetCounter + "/" + trainingSet.size() + " - " + tweetText);
            switch(scanner.nextLine()) {
                case "1":
                    database.insertTokens(collectionName, "positive", cleanTokenizedTweet);
                    break;
                case "2":
                    database.insertTokens(collectionName, "neutral", cleanTokenizedTweet);
                    break;
                case "3":
                    database.insertTokens(collectionName, "negative", cleanTokenizedTweet);
                    break;
                default:
                    break;
            }

            //increment total in mongo with one.
            tweetCounter++;
            if(tweetCounter+1 % 500 == 0) {
                //print current accuracy of model
            }
        }

        database.closeConnection();
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
