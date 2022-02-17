package datagenerator;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();
        config.setDriverClassName("com.clickhouse.jdbc.ClickHouseDriver");
        config.setUsername("default");
        config.setJdbcUrl("jdbc:ch://localhost:8124/kion");
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        var jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        jdbcTemplate.setQueryTimeout(10_000);
        return jdbcTemplate;
    }
}
