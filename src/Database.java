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
    MongoCollection collection;

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
                        incrementClassCounter(collectionName, "positive");
                        break;
                    case "neutral":
                        doc = new Document("_id", token).append("neutral" , 1);
                        incrementClassCounter(collectionName, "neutral");
                        break;
                    case "negative":
                        doc = new Document("_id", token).append("negative" , 1);
                        incrementClassCounter(collectionName, "negative");
                        break;
                }

                collection.insertOne(doc);
            } else {
                switch(classifier) {
                    case "positive":
                        collection.updateOne(eq("_id", token), inc("positive", 1));
                        incrementClassCounter(collectionName, "positive");
                        break;
                    case "neutral":
                        collection.updateOne(eq("_id", token), inc("neutral", 1));
                        incrementClassCounter(collectionName, "neutral");
                        break;
                    case "negative":
                        collection.updateOne(eq("_id", token), inc("negative", 1));
                        incrementClassCounter(collectionName, "negative");
                        break;
                }
            }
        }
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
