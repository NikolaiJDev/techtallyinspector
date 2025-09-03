package com.techtallyinspector.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.techtallyinspector.repository")
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Override
    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {
        if ("mysql".equalsIgnoreCase(databaseProperties.getType())) {
            return createMySqlConnectionFactory();
        } else {
            return createPostgreSqlConnectionFactory();
        }
    }

    private ConnectionFactory createPostgreSqlConnectionFactory() {
        return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(databaseProperties.getHost())
                .port(databaseProperties.getPort())
                .database(databaseProperties.getDatabase())
                .username(databaseProperties.getUsername())
                .password(databaseProperties.getPassword())
                .build()
        );
    }

    private ConnectionFactory createMySqlConnectionFactory() {
        return MySqlConnectionFactory.from(
            MySqlConnectionConfiguration.builder()
                .host(databaseProperties.getHost())
                .port(databaseProperties.getPort())
                .database(databaseProperties.getDatabase())
                .username(databaseProperties.getUsername())
                .password(databaseProperties.getPassword())
                .build()
        );
    }
}