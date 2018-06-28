package com.github.pedroswits;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.AuthenticationException;

import static org.neo4j.driver.v1.Values.parameters;

public class Osm2Neo4j {

    private final Driver driver;

    public Osm2Neo4j(String uri, String username, String password) throws AuthenticationException {

        driver = GraphDatabase.driver(
                uri,
                AuthTokens.basic(
                        username,
                        password),
                Config.build()
                      .withLogging(new QuietLogging())
                      .toConfig()
                );

    }


    public String getGreeting( final String message ) {
        try ( Session session = driver.session() ) {
            String greeting = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run( "CREATE (a:Greeting) " +
                                                     "SET a.message = $message " +
                                                     "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            return greeting;
        }
    }

    public void close() throws Exception {
        driver.close();
    }
}
