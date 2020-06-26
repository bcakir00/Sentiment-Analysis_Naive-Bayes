import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    @Test
    void incrementTweetsChecked() {
        Dashboard dashboard = new Dashboard();
        dashboard.incrementTweetsChecked();

        assertEquals(1, dashboard.getTweetsChecked());
    }

    @Test
    void incrementTweetsGuessedCorrectly() {
        Dashboard dashboard = new Dashboard();
        dashboard.incrementTweetsGuessedCorrectly();

        assertEquals(1, dashboard.getTweetsGuessedCorrectly());
    }

    @Test
    void incrementPositiveTweets() {
        Dashboard dashboard = new Dashboard();
        dashboard.incrementPositiveTweets();

        assertEquals(1, dashboard.getPositiveTweets());
    }

    @Test
    void incrementNeutralTweets() {
        Dashboard dashboard = new Dashboard();
        dashboard.incrementNeutralTweets();

        assertEquals(1, dashboard.getNeutralTweets());
    }

    @Test
    void incrementNegativeTweets() {
        Dashboard dashboard = new Dashboard();
        dashboard.incrementNegativeTweets();

        assertEquals(1, dashboard.getNegativeTweets());
    }
}