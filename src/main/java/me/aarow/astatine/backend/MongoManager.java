package me.aarow.astatine.backend;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.aarow.astatine.backend.settings.MongoSettings;
import me.aarow.astatine.utilities.Utility;
import org.bson.Document;

import java.util.Collections;

public class MongoManager {
    
    @Getter private MongoCollection<Document> profiles, servers, kits;

    public MongoManager(){
        MongoSettings mongoSettings = Utility.getMongoSettings();

        MongoClient mongoClient;

        if(mongoSettings.isAuthentication()){
            MongoCredential mongoCredential = MongoCredential.createCredential(mongoSettings.getUsername(), mongoSettings.getDatabase(), mongoSettings.getPassword().toCharArray());
            mongoClient = new MongoClient(new ServerAddress(mongoSettings.getHost(), mongoSettings.getPort()), Collections.singletonList(mongoCredential));
        }else{
            mongoClient = new MongoClient(mongoSettings.getHost(), mongoSettings.getPort());
        }

        MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoSettings.getDatabase());
        Utility.createCollection(mongoDatabase, "profiles");
        Utility.createCollection(mongoDatabase, "servers");
        Utility.createCollection(mongoDatabase, "kits");

        profiles = mongoDatabase.getCollection("profiles");
        servers = mongoDatabase.getCollection("servers");
        servers = mongoDatabase.getCollection("kits");
    }
}
