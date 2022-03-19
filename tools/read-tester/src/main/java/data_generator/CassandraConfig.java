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
        var config = DriverConfigLoader.programmaticBuilder()
                .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofSeconds(15))
                .withInt(DefaultDriverOption.CONNECTION_POOL_LOCAL_SIZE, 300)
                .withDuration(DefaultDriverOption.HEARTBEAT_TIMEOUT, Duration.ofSeconds(15))
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(15))
                .withString(DefaultDriverOption.REQUEST_CONSISTENCY, "LOCAL_QUORUM")
                .build();

        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("212.109.214.181", 9042))
                .addContactPoint(new InetSocketAddress("193.8.211.143", 9042))
                .addContactPoint(new InetSocketAddress("212.109.214.253", 9042))
                .withKeyspace("kion")
                .withLocalDatacenter("vla")
                .withConfigLoader(config)
                .build();

        return session;
    }
}
