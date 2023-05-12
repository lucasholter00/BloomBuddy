package com.group18.BloomBuddy;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;

public class DataBaseConnection {
    String connectionString = "mongodb+srv://BloomBuddy:BloomBuddy@cluster0.23osmgf.mongodb.net/?retryWrites=true&w=majority";    
    MongoClient client;
    MongoDatabase database;

    public DataBaseConnection(){
        client = setUpClient();
        database = setUpDatabase(client);
    }

    private MongoClient setUpClient(){
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
        // Create a new client and connect to the server
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

    private MongoDatabase setUpDatabase(MongoClient client){
        try {
            // Send a ping to confirm a successful connection
            MongoDatabase database = client.getDatabase("BloomBuddy");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            return database;
        } 
        catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    } 
    

    public void query(){
        // Query the database
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document doc = collection.find().first();
        System.out.println(doc.toJson());
    }   

    public boolean addUser(String username, String password){
        MongoCollection<Document> collection = database.getCollection("sys_user");

        Document query = new Document("username", username);
        Document result = collection.find(query).first();
        if (result != null){
            return false;
        }

        Document document = new Document("username", username)
                .append("password", password)
                .append("lastWatered", "")
                .append("profiles", Arrays.asList())
                .append("historicalData", Arrays.asList());
        collection.insertOne(document);
        return true;
    }

    public boolean verifyLogin(String username, String password){
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document query = new Document("username", username).append("password", password);
        Document result = collection.find(query).first();
        if(result != null){
            return true;
        }
        return false;
    }

    public void insertHistoricalData(HistoricalData data, String username){
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document filter = new Document("username", username);
        Document update = new Document("$push", new Document("historicalData", new Document("moisture", data.getMoisture())
                .append("temperature", data.getTemperature())
                .append("humidity", data.getHumidity())
                .append("light", data.getLight())
                .append("time", data.getTime())));
        collection.updateOne(filter, update);
    }



    public void insertLastWatered(LocalDateTime lastWatered, String profileId){
        MongoCollection<Document> collection = database.getCollection("sys_user"); //needs to be correlating to the plant

        // Create a new document representing the Plant
        Document plantDocument = new Document("$set", new Document("profiles.$.lastWatered",lastWatered));

        // Find the user document by username
        Document filter = new Document("id", profileId);

        // Replace the existing user document with the new document, as of the reason that only one lastWatered should be saved
        collection.replaceOne(filter, plantDocument);
        collection.replaceOne(filter, plantDocument);
    }


}

