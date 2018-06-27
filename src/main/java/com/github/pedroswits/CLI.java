package com.github.pedroswits;

import org.apache.commons.cli.*;

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
                "Neo4j graph database connection uri");
        uri.setRequired(true);
        options.addOption(uri);

        Option username = new Option(
                "u",
                "username",
                true,
                "Username of user in neo4j graph database");
        username.setRequired(true);
        options.addOption(username);

        Option password = new Option(
                "p",
                "password",
                true,
                "Password of user in neo4j graph database");
        password.setRequired(true);
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
        this.uri = cmd.getOptionValue("uri");
        this.username = cmd.getOptionValue("username");
        this.password = cmd.getOptionValue("password");
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

        System.out.println(cli.getOsmFilename());
        System.out.println(cli.getNeo4jURI());
        System.out.println(cli.getNeo4jUsername());
        System.out.println(cli.getNeo4jPassword());
    }
}
