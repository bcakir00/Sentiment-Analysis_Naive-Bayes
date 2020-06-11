import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.List;


public class TwitterAPI {
    private Twitter twitter;
    private String language;
    private int amount;

    public TwitterAPI() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("-")
                .setOAuthConsumerSecret("-")
                .setOAuthAccessToken("-")
                .setOAuthAccessTokenSecret("-")
                .setTweetModeExtended(true);
        TwitterFactory TwitterFactory = new TwitterFactory(cb.build());
        twitter = TwitterFactory.getInstance();
    }

    public List<Status> getTweets(String keyword) {
        List<Status> tweets = null;

        try {
            Query query = new Query();
            query.setQuery(keyword);
            query.setLang(language);
            query.setCount(amount);
            tweets = twitter.search(query).getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return tweets;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
