/**
 * Text based dashboard to show useful information about accuracy and amount of tweets in sentiment classes.
 */
public class Dashboard {
    int tweetsChecked;
    int tweetsGuessedCorrectly;
    int positiveTweets;
    int neutralTweets;
    int negativeTweets;

    /**
     * Constructor, sets all initial counter to 0
     */
    public Dashboard() {
        this.tweetsChecked = 0;
        this.tweetsGuessedCorrectly = 0;
        this.positiveTweets = 0;
        this.neutralTweets = 0;
        this.negativeTweets = 0;
    }

    /**
     * Checks how many tweets are positive, neutral or negative and prints percentages
     */
    public void printAccuracyDashboard() {
        double accuracy = calculateModelAccuracy(tweetsGuessedCorrectly, tweetsChecked);

        System.out.println("\nINFO - Accuracy: " + accuracy);
    }

    /**
     * Prints positive, neutral and negative tweets percentage respectively
     */
    public void printDashboard() {
        double positivePercentage = (double) positiveTweets / (double)tweetsChecked;
        double neutralPercentage = (double) neutralTweets / (double)tweetsChecked;
        double negativePercentage = (double) negativeTweets / (double)tweetsChecked;

        System.out.println("Positive Tweets: " + positivePercentage +
                "\nNeutral Tweets: " + neutralPercentage +
                "\nNegative Tweets: " + negativePercentage);
    }

    /**
     * Calculates accuracy of AI model
     *
     * @param correct correct tweets
     * @param total total tweets
     * @return percentage
     */
    private double calculateModelAccuracy(int correct, int total) {
        return (double) correct / (double) total;
    }

    /**
     * Increments tweetsChecked by one
     */
    public void incrementTweetsChecked() {
        this.tweetsChecked++;
    }

    /**
     * Increments tweetsGuessedCorrectly by one
     */
    public void incrementTweetsGuessedCorrectly() {
        this.tweetsGuessedCorrectly++;
    }

    /**
     * Increments positiveTweets by one
     */
    public void incrementPositiveTweets() {
        this.positiveTweets++;
    }

    /**
     * Increments neutralTweets by one
     */
    public void incrementNeutralTweets() {
        this.neutralTweets++;
    }

    /**
     * Increments negativeTweets by one
     */
    public void incrementNegativeTweets() {
        this.negativeTweets++;
    }
}
