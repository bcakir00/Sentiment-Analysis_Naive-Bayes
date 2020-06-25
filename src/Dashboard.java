public class Dashboard {
    int tweetsChecked;
    int tweetsGuessedCorrectly;

    public Dashboard() {
        tweetsChecked = 0;
        tweetsGuessedCorrectly = 0;
    }

    public void printSimpleDashboard() {
        double accuracy = calculateModelAccuracy(tweetsGuessedCorrectly, tweetsChecked);

        System.out.println("\nINFO - Accuracy: " + accuracy);
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
}
