import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Twitter API class to retrieve tweets.
 */
public class TwitterAPI {
    private Twitter twitter;
    private String language;

    /**
     * Constructor for TwitterAPI class, opens connection to twitter API through API keys en sets tweet mode to
     * extended to increase tweet character
     * limit from 140 to 280.
     */
    public TwitterAPI() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(false)
                .setOAuthConsumerKey("-")
                .setOAuthConsumerSecret("-")
                .setOAuthAccessToken("-")
                .setOAuthAccessTokenSecret("-")
                .setTweetModeExtended(true);
        TwitterFactory TwitterFactory = new TwitterFactory(cb.build());
        twitter = TwitterFactory.getInstance();
    }

    /**
     * Gets tweets based on keyword. Note: TwitterAPI has a limit on how much tweets one can retrieve. Current limit
     * is set to 15.000 tweets per 15 minutes.
     *
     * @param keyword keyword one would like to search for
     * @param limit amount of tweets to be retrieved
     * @return returns tweetsList
     */
    public List<Status> getTweets(String keyword, int limit) {
        List<Status> tweetList = new ArrayList<>();

        try {
            Query query = new Query();
            query.setQuery(keyword);
            query.setLang(language);
            query.setCount(limit);

            int tweetCount = 0;
            while(tweetCount < limit) {
                List<Status> tweets = twitter.search(query).getTweets();

                for(Status tweet : tweets) {
                    query.setMaxId(tweet.getId());
                    tweetList.add(tweet);

                    tweetCount++;
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return tweetList;
    }

    /**
     * @param language set location
     * @see <a href="https://developer.twitter.com/en/docs/twitter-for-websites/twitter-for-websites-supported-languages/overview">Twitter country codes</a>
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets to which language twitterAPI is set to
     *
     * @return two character language code
     */
    public String getLanguage() {
        return language;
    }
}
