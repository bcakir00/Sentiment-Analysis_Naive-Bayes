import twitter4j.Status;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Data data =  new Data();
        TwitterAPI twitterAPI = new TwitterAPI();
        twitterAPI.setLanguage("nl");
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        System.out.println("Welcome to the naive bayes algorithm demo. " +
                "\n- Press 1 to train dataset" +
                "\n- Press 2 to show sentiment");
        String optionSelected = scanner.nextLine();

        System.out.println("Please type in the desired topic (keyword).");
        String keyword = scanner.nextLine();
        System.out.println("Please type in the desired amount of tweets (500 recommended).");
        int amount = scanner.nextInt();
        List<Status> tweets = twitterAPI.getTweets(keyword, amount);

        switch(optionSelected) {
            case "1":
                System.out.println("\n Please classify the following tweets as 1:positive, 2:neutral, 3:negative, " +
                        "blank to discard tweet or \"exit\" to stop en delete training set.");
                List<List<Status>> splitData = data.splitTrainingData(tweets);
                data.setupTrainingData(keyword, splitData);
                break;
            case "2":
                Dashboard dashboard = data.getCurrentSentiment(keyword, tweets);
                dashboard.printDashboard();
        }
    }
}
