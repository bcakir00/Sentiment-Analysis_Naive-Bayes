import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;


public class Database {
    MongoClient mongoClient;
    MongoDatabase database;

    public Database(String name) {
        MongoClient mongoClient = MongoClients.create();
        database = mongoClient.getDatabase(name);
    }

    public void insertTokens(String collectionName, String classifier, List<String> cleanTokenizedTweet) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        for(String token : cleanTokenizedTweet) {
            if(collection.find(eq("_id", token)).first() == null) {
                Document doc = new Document();

                switch(classifier) {
                    case "positive":
                        doc = new Document("_id", token).append("positive" , 1);
                        incrementWordClassCounter(collectionName, "positive");
                        break;
                    case "neutral":
                        doc = new Document("_id", token).append("neutral" , 1);
                        incrementWordClassCounter(collectionName, "neutral");
                        break;
                    case "negative":
                        doc = new Document("_id", token).append("negative" , 1);
                        incrementWordClassCounter(collectionName, "negative");
                        break;
                }

                collection.insertOne(doc);
            } else {
                switch(classifier) {
                    case "positive":
                        collection.updateOne(eq("_id", token), inc("positive", 1));
                        incrementWordClassCounter(collectionName, "positive");
                        break;
                    case "neutral":
                        collection.updateOne(eq("_id", token), inc("neutral", 1));
                        incrementWordClassCounter(collectionName, "neutral");
                        break;
                    case "negative":
                        collection.updateOne(eq("_id", token), inc("negative", 1));
                        incrementWordClassCounter(collectionName, "negative");
                        break;
                }
            }
        }
    }

    private void incrementWordClassCounter(String collectionName, String classifier) {
        MongoCollection<Document> collection_total = database.getCollection(collectionName + "_totals");

        if(collection_total.find(eq("_id", "wordTotal")).first() == null) {
            Document doc = new Document("_id", "wordTotal")
                    .append("positive" , 0)
                    .append("neutral" , 0)
                    .append("negative" , 0);
            collection_total.insertOne(doc);
        }

        collection_total.updateOne(eq("_id", "wordTotal"), inc(classifier, 1));
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
