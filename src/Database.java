import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Database {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection collection;

    public Database(String name) {
        MongoClient mongoClient = new MongoClient();
        database = mongoClient.getDatabase(name);
    }

    public void insertOneDocument(String collectionName, Document document) {
        collection = database.getCollection(collectionName);
        collection.insertOne(document);
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
