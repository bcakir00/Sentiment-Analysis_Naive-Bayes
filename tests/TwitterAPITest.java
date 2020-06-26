import org.junit.jupiter.api.Test;
import twitter4j.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwitterAPITest {

    @Test
    void getTweets() {
        TwitterAPI api = new TwitterAPI();
        List<Status> tweets = api.getTweets("test", 5);

        assertEquals(5, tweets.size());
    }

    @Test
    void setLanguage() {
        TwitterAPI api = new TwitterAPI();
        api.setLanguage("nl");

        assertEquals("nl", api.getLanguage());
    }
}