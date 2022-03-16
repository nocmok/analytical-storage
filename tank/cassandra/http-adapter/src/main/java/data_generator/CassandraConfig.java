package data_generator;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.time.Duration;

@Configuration
public class CassandraConfig {

    @Bean
    public CqlSession cqlSession() {
        DriverConfigLoader loader =
                DriverConfigLoader.programmaticBuilder()
                        .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(10))
                        .withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "10 seconds")
                        .withInt(DefaultDriverOption.CONNECTION_POOL_LOCAL_SIZE, 100)
                        .build();

        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("212.109.214.181", 9042))
                .addContactPoint(new InetSocketAddress("193.8.211.143", 9042))
                .addContactPoint(new InetSocketAddress("212.109.214.253", 9042))
                .withKeyspace("kion")
                .withLocalDatacenter("vla")
                .withConfigLoader(loader)
                .build();

        return session;
    }
}
