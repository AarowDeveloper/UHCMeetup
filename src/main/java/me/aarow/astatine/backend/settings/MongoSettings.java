package me.aarow.astatine.backend.settings;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MongoSettings {

    private String host;
    private int port;
    private String database;
    private boolean authentication;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
