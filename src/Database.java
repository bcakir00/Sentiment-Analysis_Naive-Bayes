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

    public void insertOneDocument(String collectionName, Document document) {
        collection = database.getCollection(collectionName);
        collection.insertOne(document);
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
