package usage_examples;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.MongoCollection;

public class FindOne
{
    public static void main( String[] args )
    {
        // Replace the uri string with your MongoDB deployment's connection string
        MongoClientURI uri = new MongoClientURI("mongodb+srv://<user>:<password>@<cluster-url>?retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);

        MongoDatabase database = mongoClient.getDatabase("sample_mflix");
        MongoCollection<Document> collection = database.getCollection("movies");

        Bson projectionFields = Projections.fields(
                Projections.include("title", "imdb"),
                Projections.excludeId());

        Document doc = collection.find(eq("title", "The Room"))
                .projection(projectionFields)
                .sort(Sorts.descending("rating"))
                .first();

        if (doc == null) {
            System.out.println("No results found.");
        } else {
            System.out.println(doc.toJson());
        }
        mongoClient.close();
    }
}
