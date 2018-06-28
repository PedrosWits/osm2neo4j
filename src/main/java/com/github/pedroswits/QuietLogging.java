package com.github.pedroswits;

import org.neo4j.driver.internal.logging.JULogger;
import org.neo4j.driver.v1.Logger;
import org.neo4j.driver.v1.Logging;

import java.util.logging.Level;

public class QuietLogging implements Logging
{
    private final Level loggingLevel = Level.WARNING;

    @Override
    public  Logger getLog( String name ) {
        return new JULogger(name, loggingLevel);
    }
}