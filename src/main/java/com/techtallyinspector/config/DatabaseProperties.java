package com.techtallyinspector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.database")
public class DatabaseProperties {

    private String type = "postgresql"; // default to postgresql
    private String host = "localhost";
    private int port = 5432;
    private String database = "techtallyinspector";
    private String username = "postgres";
    private String password = "postgres";

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcUrl() {
        if ("mysql".equalsIgnoreCase(type)) {
            return String.format("jdbc:mysql://%s:%d/%s", host, port, database);
        } else {
            return String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        }
    }

    public String getR2dbcUrl() {
        if ("mysql".equalsIgnoreCase(type)) {
            return String.format("r2dbc:mysql://%s:%d/%s", host, port, database);
        } else {
            return String.format("r2dbc:postgresql://%s:%d/%s", host, port, database);
        }
    }
}