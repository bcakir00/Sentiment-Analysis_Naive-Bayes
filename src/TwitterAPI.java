import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


public class TwitterAPI {
    private Twitter twitter;
    private String language;

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

    public List<Status> getTweets(String keyword, int limit) {
        List<Status> tweetss = new ArrayList<>();

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
                    tweetss.add(tweet);

                    tweetCount++;
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return tweetss;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
