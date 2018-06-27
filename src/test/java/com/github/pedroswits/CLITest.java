package com.github.pedroswits;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class CLITest {

  @Test
  public void parsesArgs() {
    String osmfilename = "newcastle.osm";
    String uri = "doesnotmatter";
    String username = "tester";
    String password = "abc123";

    String[] args = new String[]{
      "-i", osmfilename,
      "-k", uri,
      "-u", username,
      "-p", password
    };

    CLI cli = new CLI("Test CLI", args);

    assertEquals(cli.getOsmFilename(), osmfilename);
    assertEquals(cli.getNeo4jURI(), uri);
    assertEquals(cli.getNeo4jUsername(), username);
    assertEquals(cli.getNeo4jPassword(), password);
  }
}
