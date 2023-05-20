package com.group18.BloomBuddy;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public void insertHistoricalData(HistoricalData data, String profileId){
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document filter = new Document("profiles.id", profileId);
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
                .append("lastWatered", "")
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


    public void insertLastWatered(LocalDateTime lastWatered, String id){
        MongoCollection<Document> collection = database.getCollection("sys_user"); //needs to be correlating to the plant

        // Create a new document representing the Plant
        Document plantDocument = new Document("$set", new Document("profiles.$.lastWatered",lastWatered));

        // Find the user document by username
        Document filter = new Document("profiles.id", id);

        // Replace the existing user document with the new document, as of the reason that only one lastWatered should be saved
        collection.updateOne(filter, plantDocument);
    }

    public void editSensorSettings(String type, float value, String profileID){
        System.out.println("Hello");
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document filter = new Document("profiles.id", profileID);
        String path = "profiles.$.sensorSettings." + type;
        Document updateThreshold = new Document("$set", new Document(path, value));
        collection.updateOne(filter, updateThreshold);
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
                Date date = data.getDate("time");
                LocalDateTime time = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                if (time.isAfter(from) && time.isBefore(until)) {
                    filteredData.add(data);
                }
            }
            return filteredData;
        }

        return null; //if filteredData can not be returned, the return value will be null.
    }



    public SensorSettings getSensorSettings(String profileId) {
        MongoCollection<Document> collection = database.getCollection("sys_user");

        Document filter = new Document("profiles.id", profileId);

        Document document = collection.find(filter).first();

        if (document != null) {
            //This code retrieves the profile document with the specified profile ID from the document object
            // by filtering the list of profiles based on the ID and returning the first matching profile document,
            // or null if not found
            Document profileDoc = document.getList("profiles", Document.class)
                    .stream()
                    .filter(profile -> profile.getString("id").equals(profileId))
                    .findFirst()
                    .orElse(null);

            if (profileDoc != null) {
                Document sensorSettingsDoc = profileDoc.get("sensorSettings", Document.class);

                if (sensorSettingsDoc != null) {
                    Float temperatureLowerBound = sensorSettingsDoc.getDouble("tempratureThresholdLow").floatValue();
                    Float temperatureUpperBound = sensorSettingsDoc.getDouble("tempratureThresholdHigh").floatValue();
                    Float humidityLowerBound = sensorSettingsDoc.getDouble("humidityThresholdLow").floatValue();
                    Float humidityUpperBound = sensorSettingsDoc.getDouble("humidityThresholdHigh").floatValue();
                    Float moistureLowerBound = sensorSettingsDoc.getDouble("moistureThresholdLow").floatValue();
                    Float moistureUpperBound = sensorSettingsDoc.getDouble("moistureThresholdHigh").floatValue();
                    Float lightLowerBound = sensorSettingsDoc.getDouble("lightThresholdLow").floatValue();
                    Float lightUpperBound = sensorSettingsDoc.getDouble("lightThresholdHigh").floatValue();

                    return new SensorSettings(
                            temperatureLowerBound != null ? temperatureLowerBound : 0.0f,
                            temperatureUpperBound != null ? temperatureUpperBound : 0.0f,
                            humidityLowerBound != null ? humidityLowerBound : 0.0f,
                            humidityUpperBound != null ? humidityUpperBound : 0.0f,
                            moistureLowerBound != null ? moistureLowerBound : 0.0f,
                            moistureUpperBound != null ? moistureUpperBound : 0.0f,
                            lightLowerBound != null ? lightLowerBound : 0.0f,
                            lightUpperBound != null ? lightUpperBound : 0.0f
                    );
                }
            }
        }

        return null;
    }

    public void editProfileName(String newName, String profileID){
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document filter = new Document("profiles.id", profileID);
        Document updateName = new Document("$set", new Document("profiles.$.name", newName));
        collection.updateOne(filter, updateName);
    }

    public List<Profile> getProfiles(String username) throws MqttException{
        MongoCollection<Document> collection = database.getCollection("sys_user");
        Document filter = new Document("username", username);
        Document user = collection.find(filter).first();

        List<Profile> profileList = new ArrayList<>();
        if(user != null){
            System.out.println("User found");

            List<Document> profiles = (List<Document>) user.get("profiles");
            for(Document profile : profiles){
                String name = (String) profile.get("name"); 
                String id = (String) profile.get("id");
                String lastWatered = (String) profile.get("lastWatered");

                Document sensorSettings = (Document) profile.get("sensorSettings");
                double temperatureLowerBound = sensorSettings.getDouble("tempratureThresholdLow");
                double temperatureUpperBound = sensorSettings.getDouble("tempratureThresholdHigh");
                double humidityLowerBound = sensorSettings.getDouble("humidityThresholdLow");
                double humidityUpperBound = sensorSettings.getDouble("humidityThresholdHigh");
                double moistureLowerBound = sensorSettings.getDouble("moistureThresholdLow");
                double moistureUpperBound = sensorSettings.getDouble("moistureThresholdHigh");
                double lightLowerBound = sensorSettings.getDouble("lightThresholdLow");
                double lightUpperBound = sensorSettings.getDouble("lightThresholdHigh");

                SensorSettings settings = new SensorSettings((float)temperatureLowerBound, (float)temperatureUpperBound,  (float)moistureLowerBound, (float)moistureUpperBound, (float)lightLowerBound, (float)lightUpperBound, (float)humidityLowerBound, (float)humidityUpperBound);
                Profile dbProfile = new Profile(settings, name, id);

                List<Document> historicalData = (List<Document>)profile.get("HistoricalData");
                if(historicalData != null){
                    for(Document data :  historicalData){
                        double temperature = (double)data.get("temperature");
                        double humidity = (double)data.get("humidity");
                        double moisture = (double)data.get("moisture");
                        double light = (double)data.get("light");
                        Date date = (Date)data.get("time");

                        LocalDateTime time = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

                        HistoricalData dbHisData = new HistoricalData((float)moisture, (float)temperature, (float)humidity, (float)light, time);

                        dbProfile.addHistoricalData(dbHisData);


                    }
                }
                profileList.add(dbProfile);
            }

        }
        return profileList;

    }

    public void close(){
        client.close();
    }
}

