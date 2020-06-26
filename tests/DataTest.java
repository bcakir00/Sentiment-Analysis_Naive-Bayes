import org.junit.jupiter.api.Test;
import twitter4j.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {

    @Test
    void splitTrainingData() {
        TwitterAPI api = new TwitterAPI();
        List<Status> list = api.getTweets("test", 5);

        Data data = new Data();
        List<List<Status>> splitList = data.splitTrainingData(list);

        assertEquals(1, splitList.get(1).size());
    }
}