package data_generator;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CassandraConfig {

    @Bean
    public CqlSession cqlSession() {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withKeyspace("kion")
                .withLocalDatacenter("datacenter1")
                .build();

        return session;
    }
}
