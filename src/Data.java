import com.mongodb.client.MongoCollection;
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
        int tweetCounter = 1;

        for(Status tweet : tweets) {
            if(tweetCounter % 5 == 0) {
                testingSet.add(tweet);
            } else {
                trainingSet.add(tweet);
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
        Dashboard dashboard = new Dashboard();
        NaiveBayes naiveBayes = new NaiveBayes();
        List<Status> trainingSet = set.get(0);
        List<Status> testingSet = set.get(1);
        int totalSetSize = set.get(0).size() + set.get(1).size();
        int tweetCounter = 1;
        int trainingTweetCounter = 0;
        int testingTweetCounter = 0;
        String tweetClassification;
        String tweetText;
        String collectionTypeName;

        for(int i = 1; i <= totalSetSize; i++) {
            if(i % 5 == 0) {
                tweetText = getText(testingSet.get(testingTweetCounter));
                testingTweetCounter++;
                collectionTypeName = collectionName + "_testing";
            } else {
                tweetText = getText(trainingSet.get(trainingTweetCounter));
                trainingTweetCounter++;
                collectionTypeName = collectionName + "_training";
            }

            System.out.println(tweetCounter + "/" + totalSetSize + " - " + tweetText);

            String[] tokenizedTweet = tokenizeTweet(tweetText);
            List<String> cleanTokenizedTweet = cleanTweet(tokenizedTweet);

            String input = scanner.nextLine();
            switch(input) {
                case "1":
                    database.insertTokens(collectionTypeName, "positive", cleanTokenizedTweet);
                    database.incrementTweetClassCounter(collectionTypeName, "positive");
                    break;
                case "2":
                    database.insertTokens(collectionTypeName, "neutral", cleanTokenizedTweet);
                    database.incrementTweetClassCounter(collectionTypeName, "neutral");
                    break;
                case "3":
                    database.insertTokens(collectionTypeName, "negative", cleanTokenizedTweet);
                    database.incrementTweetClassCounter(collectionTypeName, "negative");
                    break;
                default:
                    break;
            }

            if(i % 5 == 0) {
                input = convertInputForHumans(input);
                tweetClassification = naiveBayes.classifyTweet(database, collectionName + "_training", cleanTokenizedTweet);
                dashboard.incrementTweetsChecked();

                if(tweetClassification.equals(input)) {
                    dashboard.incrementTweetsGuessedCorrectly();
                }

                dashboard.printAccuracyDashboard();
            }

            tweetCounter++;
        }
    }

    private String getText(Status tweet) {
        String tweetText;

        if(tweet.isRetweet()){
            tweetText = "RETWEET " + tweet.getRetweetedStatus().getText();
        } else {
            tweetText = tweet.getText();
        }

        return tweetText;
    }

    private List<String> cleanTweet(String[] splitTweetText) {
        List<String> cleanedTokens = new ArrayList<>();
        String[] stopSymbols = {"[.]", ",", ":", ";", "#", "@", "!", "\\?", "'", "\""};
        String[] stopWords = {"van", "de", "het", "tussen", "over", "ook", "is", "of", "met",
                "doen", "heeft", "onze", "maar", "hun", "onze", "terwijl", "deze", "nou", "mee", "die", "nog", "nóg",
                "en", "we", "wij", "gewoon", "er", "zich", "wat", "dit", "als", "naar", "te", "met", "voor", "uit",
                "in", "dat", "es", "ons", "onze", "retweet", "op", "al"};

        for(String word : splitTweetText) {
            word = word.toLowerCase();

            for(String symbol : stopSymbols) {
                word = word.replaceAll(symbol, "");
            }

            for(String stopword : stopWords) {
                word = word.replaceAll("\\b" + stopword + "\\b", "");
            }

            if(Pattern.matches("[a-zA-Z0-9]*", word) && !word.contains("https") &&!word.isEmpty()) {
                cleanedTokens.add(word);
            }
        }

        return cleanedTokens;
    }

    private String[] tokenizeTweet(String tweetText) {
        return tweetText.split("\\s+");
    }

    private String convertInputForHumans(String input) {
        String answer;

        switch(input) {
            case "1":
                answer = "positive";
                break;
            case "2":
                answer = "neutral";
                break;
            case "3":
                answer = "negative";
                break;
            default:
                answer = null;
                break;
        }

        return answer;
    }

    public Dashboard getCurrentSentiment(String collectionName, List<Status> tweets) {
        Database database =  new Database("IPASS");
        NaiveBayes naiveBayes = new NaiveBayes();
        Dashboard dashboard = new Dashboard();

        if(!database.checkIfCollectionExists(collectionName)) {
            collectionName = "economie";
        }

        for(int i = 0; i < tweets.size(); i++) {
            String tweetText = getText(tweets.get(i));

            System.out.println("classifying tweet " + (i + 1) + "/" + tweets.size() + " - " + tweetText);

            String[] tokenizedTweet = tokenizeTweet(tweetText);
            List<String> cleanTokenizedTweet = cleanTweet(tokenizedTweet);

            String tweetClassification = naiveBayes.classifyTweet(database, collectionName + "_training",
                    cleanTokenizedTweet);
            dashboard.incrementTweetsChecked();

            switch(tweetClassification) {
                case "positive":
                    dashboard.incrementPositiveTweets();
                    break;
                case "neutral":
                    dashboard.incrementNeutralTweets();
                    break;
                case "negative":
                    dashboard.incrementNegativeTweets();
                    break;
            }

            System.out.println("Verdict: " + tweetClassification + "\n");
        }

        return dashboard;
    }
}
