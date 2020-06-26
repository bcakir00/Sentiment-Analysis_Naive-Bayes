import java.math.BigDecimal;
import java.util.List;

/**
 * Tweet classifier.
 */
public class NaiveBayes {
    /**
     * Classifies tweets based on the naive bayes algorithm. Which is a supervised algorithm, meaning that the developer
     * has to give the model a training set that has been modified by the developer himself.
     *
     * The bayes theorem accounts for the context of a problem. So when asking if a certain person has a certain feature
     * like the color of their eyes. The context matters as people from the Middle East have a far higher chance of
     * their eyes being dark, instead of light compared to Europeans.
     *
     * The Naive bayes algorithm thus checks how great the probability/likelihood is that a word/token is part of a
     * certain classifier (i.e. positive, neutral, negative). This probability/likelihood is multiplied by all
     * words/tokens and with the prior probability. We do this for every class and the class with the highest
     * probability/likelihood wins.
     *
     * Note: Naive bayes' accuracy ranges from situation to situation. In this specific situation, I managed to get a
     * score of around 75% to 80%. Please be aware of overfitting the model, this can be avoided by skipping the
     * retweets that have already been added to the model and by spacing out the training of the model. Since some words
     * like brands may be in a negative spotlight on a certain day.
     *
     * Second note: Model generally doesn't improve significantly after 500 tweets.
     *
     * @param database database object
     * @param collectionName name of collection
     * @param cleanTokenizedTweet list of clean and tokenized tweet
     * @return classifier (i.e. positive, neutral, negative)
     */
    public String classifyTweet(Database database, String collectionName, List<String> cleanTokenizedTweet) {
        String[] classes = {"positive", "neutral", "negative"};
        BigDecimal chance;
        BigDecimal chancePositive = new BigDecimal(0);
        BigDecimal chanceNeutral = new BigDecimal(0);
        BigDecimal chanceNegative = new BigDecimal(0);
        String result;

        for(String classification : classes) {
            chance = new BigDecimal(calculatePriorProbability(database, collectionName, classification));

            if(chance.doubleValue() > (double) 0) {
                for(String token : cleanTokenizedTweet) {
                    BigDecimal temp = new BigDecimal(calculateProbabilityGivenClass(database, collectionName, classification, token));

                    if(temp.doubleValue() > (double) 0) {
                        chance = chance.multiply(temp);
                    }
                }
            }

            switch(classification) {
                case "positive":
                    chancePositive = chance;
                    break;
                case "neutral":
                    chanceNeutral = chance;
                    break;
                case "negative":
                    chanceNegative = chance;
                    break;
            }
        }

        if(chancePositive.compareTo(chanceNeutral) == 1 && chancePositive.compareTo(chanceNegative) == 1) {
            result = "positive";
        } else if(chanceNegative.compareTo(chancePositive) == 1 && chanceNegative.compareTo(chanceNeutral) == 1) {
            result = "negative";
        } else {
            result = "neutral";
        }

        return result;
    }

    /**
     * Calculates prior probability, which is nothing more than a fancy word for the ratio of a classifier to the total
     * set
     *
     * @param database database object
     * @param collectionName name of collection
     * @param classifier type of classifier
     * @return percentage of probability
     */
    public double calculatePriorProbability(Database database, String collectionName, String classifier) {
        int classifierTotal = database.getClassifierTotal(collectionName, classifier);
        int totalSet = database.getTotalSet(collectionName);

        try {
            return (double) classifierTotal / (double) totalSet;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Calculates probability that a token/word is part of a certain classifier
     *
     * @param database database object
     * @param collectionName name of collection
     * @param classifier type of classifier
     * @param token token/word
     * @return percentage of probability
     */
    public double calculateProbabilityGivenClass(Database database, String collectionName,
                                                 String classifier, String token) {
        int zeroFrequencyOffset = 1;
        int classifierWordTotal = database.getClassifierWordTotal(collectionName, classifier, token)
                + zeroFrequencyOffset;
        int totalWordSet = database.getTotalWordSet(collectionName) + zeroFrequencyOffset;

        try {
            return (double) classifierWordTotal / (double) totalWordSet;
        } catch(Exception e) {
            return 0;
        }
    }
}
