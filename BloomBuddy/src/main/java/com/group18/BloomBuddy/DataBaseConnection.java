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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

//This class will be responsible for communication with the database
public class DataBaseConnection {
    String connectionString = "mongodb+srv://BloomBuddy:BloomBuddy@cluster0.23osmgf.mongodb.net/?retryWrites=true&w=majority";    
    MongoClient client;
    MongoDatabase database;

    public DataBaseConnection(){
        client = setUpClient();
        database = setUpDatabase(client);
    }

    //This method instantiates a MongoClient object and returns it
    private MongoClient setUpClient(){
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
        // Create a new client and connect to the server
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

    //This method instantiates a MongoDatabase object and returns it
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
    
    //This method adds a user to the database
    public boolean addUser(String username, String password){
        MongoCollection<Document> collection = database.getCollection("sys_user");

        Document query = new Document("username", username);
        Document result = collection.find(query).first();
        if (result != null){
            return false;
        }

        Document document = new Document("username", username)
                .append("password", password)
                .append("profiles", Arrays.asList());
        collection.insertOne(document);
        return true;
    }

    //This method verifies the login credentials of a user
    public boolean verifyLogin(String username, String password){
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document query = new Document("username", username).append("password", password);
        Document result = collection.find(query).first();
        if(result != null){
            return true;
        }
        return false;
    }

    //This method inserts historical data into a profile that belongs to a user
    public void insertHistoricalData(HistoricalData data, String username, String profileId){
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document filter = new Document("username", username).append("profiles.id", profileId);
        Document update = new Document("$addToSet", new Document("profiles.$.HistoricalData", new Document("moisture", data.getMoisture())
                .append("temperature", data.getTemperature())
                .append("humidity", data.getHumidity())
                .append("light", data.getLight())
                .append("time", data.getTime())));
        collection.updateOne(filter, update);
    }

    //This method inserts a profile that belongs to a user
    public boolean addProfile(Profile profile, String username){
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document filter = new Document("username", username);
        Document checkForId = new Document("profiles", new Document("$elemMatch", new Document("id", profile.getId()))); 
        long count = collection.countDocuments(new Document("$and", Arrays.asList(filter, checkForId)));
        if (count > 0)
            return false;

        Document query = new Document("name", profile.getName())
                .append("id", profile.getId())
                .append("sensorSettings", new Document("tempratureThresholdLow", profile.getSensorSettings().getTemperatureLowerBound())
                        .append("tempratureThresholdHigh", profile.getSensorSettings().getTemperatureUpperBound())
                        .append("humidityThresholdLow", profile.getSensorSettings().getHumidityLowerBound())
                        .append("humidityThresholdHigh", profile.getSensorSettings().getHumidityUpperBound())
                        .append("moistureThresholdLow", profile.getSensorSettings().getMoistureLowerBound())
                        .append("moistureThresholdHigh", profile.getSensorSettings().getMoistureUpperBound())
                        .append("lightThresholdLow", profile.getSensorSettings().getLightLowerBound())
                        .append("lightThresholdHigh", profile.getSensorSettings().getLightUpperBound())
                        .append("HistoricalData", Arrays.asList()));
                        

        Document update = new Document("$addToSet", new Document("profiles", query)); 

        collection.updateOne(filter, update);
        return true;
    }

    //This method is a getter which allows search for historical data from date "from" until date "until"
    public List<Document> getHistoricalData(String username, String profileId, LocalDateTime from, LocalDateTime until) {
        MongoCollection<Document> collection = database.getCollection("sys_user");

        // Find the user document by username
        Document filter = new Document("username", username);

        // Project only the profiles which have id = profileID
        Document projection = new Document("profiles", new Document("$elemMatch", new Document("id", profileId)));

        // Execute the query to get the result
        Document result = collection.find(filter).projection(projection).first();

        // Extract the historical data array from the profile that match profileID
        List<Document> historicalData = null;
        if (result != null) {
            List<Document> profiles = (List<Document>) result.get("profiles");
            if (profiles != null && !profiles.isEmpty()) {
                Document profile = profiles.get(0);
                historicalData = (List<Document>) profile.get("HistoricalData");
            }
        }

        // Filter the historical data with regards to date range
        if (historicalData != null && !historicalData.isEmpty()) {
            List<Document> filteredData = new ArrayList<>();
            for (Document data : historicalData) {
                LocalDateTime time = (LocalDateTime) data.get("time");
                if (time.isAfter(from) && time.isBefore(until)) {
                    filteredData.add(data);
                }
            }
            return filteredData;
        }

        return null; //if filteredData can not be returned, the return value will be null.
    }

    public void close(){
        client.close();
    }
}

