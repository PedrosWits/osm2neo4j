package com.github.pedroswits;

import org.apache.commons.cli.*;
import org.neo4j.driver.internal.logging.JULogger;
import org.neo4j.driver.v1.exceptions.AuthenticationException;
import org.neo4j.logging.Level;

import java.util.logging.Logger;

public class CLI {

    private String name;
    private Options options;
    private CommandLine cmd;

    private String uri;
    private String username;
    private String password;
    private String osmfilename;

    public CLI(String name, String... args) {
        this.name = name;
        this.definition();
        this.parsing(args);
        this.interrogation();
    }

    private void definition() {
        options = new Options();

        Option osmfile = new Option(
                "i",
                "osm-file",
                true,
                "OpenStreetMap input file");
        osmfile.setRequired(true);
        options.addOption(osmfile);

        Option uri = new Option(
                "k",
                "uri",
                true,
                "Neo4j graph database connection uri [bolt://localhost:7687]");
        uri.setRequired(false);
        options.addOption(uri);

        Option username = new Option(
                "u",
                "username",
                true,
                "Username of user in neo4j graph database [neo4j]");
        username.setRequired(false);
        options.addOption(username);

        Option password = new Option(
                "p",
                "password",
                true,
                "Password of user in neo4j graph database [neo4j]");
        password.setRequired(false);
        options.addOption(password);
    }

    private void parsing(String... args) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(this.name, options);

            System.exit(1);
        }
    }

    protected void interrogation() {
        this.osmfilename = cmd.getOptionValue("osm-file");
        this.uri = cmd.getOptionValue("uri", "bolt://localhost:7687");
        this.username = cmd.getOptionValue("username", "neo4j");
        this.password = cmd.getOptionValue("password", "neo4j");
    }

    public String getOsmFilename() {
      return this.osmfilename;
    }

    public String getNeo4jURI() {
      return this.uri;
    }

    public String getNeo4jUsername() {
      return this.username;
    }

    public String getNeo4jPassword() {
      return this.password;
    }

    public static void main(String... args ) throws Exception {

        CLI cli = new CLI("Import an OpenStreetMap file to Neo4j", args);

        System.out.println(
          "Connecting to Neo4j at " +
          cli.getNeo4jURI() +
          " as user " +
          cli.getNeo4jUsername());


        Osm2Neo4j mapConverter = null;

        try {
            mapConverter =
                    new Osm2Neo4j(cli.getNeo4jURI(),
                                  cli.getNeo4jUsername(),
                                  cli.getNeo4jPassword());

        } catch (AuthenticationException exception) {
            System.out.println("Authentication error: " + exception.getMessage());
            System.exit(1);
        }


        String greeting = mapConverter.getGreeting("Hello World!");
        System.out.println(greeting);

        System.out.println(
                "Closing connection to Neo4j at " +
                        cli.getNeo4jURI() +
                        " as user " +
                        cli.getNeo4jUsername());
        mapConverter.close();
    }
}
