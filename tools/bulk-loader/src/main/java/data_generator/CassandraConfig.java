package data_generator;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.time.Duration;

@Configuration
public class CassandraConfig {

    @Value("${cassandra.contact_points}")
    private String contactPoints;
    @Value("${cassandra.n_clients}")
    private Integer nClients;

    @Bean
    public CqlSession cqlSession() {
        DriverConfigLoader loader =
                DriverConfigLoader.programmaticBuilder()
                        .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(20))
                        .withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "20 seconds")
                        .withDuration(DefaultDriverOption.HEARTBEAT_TIMEOUT, Duration.ofSeconds(20))
                        .withInt(DefaultDriverOption.CONNECTION_POOL_LOCAL_SIZE, nClients)
                        .build();

        var sessionBuilder = CqlSession.builder()
                .withKeyspace("kion")
                .withLocalDatacenter("vla")
                .withConfigLoader(loader);

        for (String hostPort : contactPoints.split(",")) {
            hostPort = hostPort.trim();
            sessionBuilder.addContactPoint(new InetSocketAddress(hostPort.split(":")[0], Integer.parseInt(hostPort.split(":")[1])));
        }

        return sessionBuilder.build();
    }
}
