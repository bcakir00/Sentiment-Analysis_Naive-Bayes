public class Dashboard {
    int tweetsChecked;
    int tweetsGuessedCorrectly;

    public Dashboard() {
        tweetsChecked = 0;
        tweetsGuessedCorrectly = 0;
    }

    public void printSimpleDashboard() {
        int accuracy = calculateModelAccuracy(tweetsGuessedCorrectly, tweetsChecked);

        System.out.println(accuracy);
    }

    private int calculateModelAccuracy(int correct, int total) {
        return correct / total;
    }

    public void incrementTweetsChecked() {
        this.tweetsChecked++;
    }

    public void incrementTweetsGuessedCorrectly() {
        this.tweetsGuessedCorrectly++;
    }
}
