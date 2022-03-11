package data_generator;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Override
    public MongoClient mongoClient() {
        var mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://default:default@localhost:27017/kion?authSource=admin"))
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override protected String getDatabaseName() {
        return "kion";
    }
}
