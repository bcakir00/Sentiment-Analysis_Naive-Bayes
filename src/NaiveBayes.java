import java.math.BigDecimal;
import java.util.List;

public class NaiveBayes {
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

    public double calculatePriorProbability(Database database, String collectionName, String classifier) {
        int classifierTotal = database.getClassifierTotal(collectionName, classifier);
        int totalSet = database.getTotalSet(collectionName);

        try {
            return (double) classifierTotal / (double) totalSet;
        } catch (Exception e) {
            return 0;
        }
    }

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
