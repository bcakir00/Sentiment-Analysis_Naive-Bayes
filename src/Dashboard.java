public class Dashboard {
    int tweetsChecked;
    int tweetsGuessedCorrectly;
    int positiveTweets;
    int neutralTweets;
    int negativeTweets;

    public Dashboard() {
        this.tweetsChecked = 0;
        this.tweetsGuessedCorrectly = 0;
    }

    public Dashboard(int positiveTweets, int neutralTweets, int negativeTweets) {
        this.positiveTweets = positiveTweets;
        this.neutralTweets = neutralTweets;
        this.negativeTweets = negativeTweets;
    }

    public void printAccuracyDashboard() {
        double accuracy = calculateModelAccuracy(tweetsGuessedCorrectly, tweetsChecked);

        System.out.println("\nINFO - Accuracy: " + accuracy);
    }

    public void printDashboard() {
        double positivePercentage = (double) positiveTweets / (double)tweetsChecked;
        double neutralPercentage = (double) neutralTweets / (double)tweetsChecked;
        double negativePercentage = (double) negativeTweets / (double)tweetsChecked;

        System.out.println("Positive Tweets: " + positivePercentage +
                "\nNeutral Tweets: " + neutralPercentage +
                "\nNegative Tweets: " + negativePercentage);
    }

    private double calculateModelAccuracy(int correct, int total) {
        return (double) correct / (double) total;
    }

    public void incrementTweetsChecked() {
        this.tweetsChecked++;
    }

    public void incrementTweetsGuessedCorrectly() {
        this.tweetsGuessedCorrectly++;
    }

    public void incrementPositiveTweets() {
        this.positiveTweets++;
    }

    public void incrementNeutralTweets() {
        this.neutralTweets++;
    }

    public void incrementNegativeTweets() {
        this.negativeTweets++;
    }
}
