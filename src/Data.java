import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
    public List<List<Status>> setupTrainingData(List<Status> tweets) {
        List<List<Status>> compositeTweets = new ArrayList<>();
        List<Status> positiveTweets = new ArrayList<>();
        List<Status> neutralTweets = new ArrayList<>();
        List<Status> negativeTweets = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int tweetCounter = 0;


        System.out.println("\n Please classify the following tweets as 1:positive, 2:neutral, 3:negative or no input to discard tweet.");
        for(Status tweet: tweets) {
            System.out.println(tweetCounter + "/" + tweets.size() + " - " + tweet.getText());

            int input = scanner.nextInt();
            switch(input) {
                case 1:
                    positiveTweets.add(tweet);
                    break;
                case 2:
                    neutralTweets.add(tweet);
                    break;
                case 3:
                    negativeTweets.add(tweet);
                    break;
                default:
                    break;
            }

            tweetCounter++;
        }

        compositeTweets.add(positiveTweets);
        compositeTweets.add(neutralTweets);
        compositeTweets.add(negativeTweets);
        return compositeTweets;
    }
}
