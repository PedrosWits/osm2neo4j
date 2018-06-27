package com.github.pedroswits;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class Osm2Neo4j {

    private final Driver driver;

    public Osm2Neo4j(String uri, String username, String password) {
        driver = GraphDatabase.driver(
                uri,
                AuthTokens.basic(
                        username,
                        password));

    }

    public void close() throws Exception {
        driver.close();
    }
}
