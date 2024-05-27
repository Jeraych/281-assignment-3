package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  private Graph graph;
  private List<Country> countryList;
  private String input;
  private boolean invalidInput;
  private Country country;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    String[] currentCountryDetails;
    String[] currentAdjacencyDetails;
    graph = new Graph();
    countryList = new ArrayList<>();

    // add code here to create your data structures

    // add nodes
    for (String i : countries) {
      currentCountryDetails = i.split(",");
      country =
          new Country(
              currentCountryDetails[0],
              currentCountryDetails[1],
              Integer.parseInt(currentCountryDetails[2]));
      graph.addNode(country);
      countryList.add(country);
    }

    // add edges
    for (int i = 0; i < adjacencies.size(); i++) {
      currentAdjacencyDetails = adjacencies.get(i).split(",");
      country = countryList.get(i);
      for (int j = 1; j < currentAdjacencyDetails.length; j++) {
        try {
          graph.addEdge(country, graph.getCountry(currentAdjacencyDetails[j]));
        } catch (InvalidCountry e) {
          return;
        }
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
    // ask for country and check if it exists repeatively
    invalidInput = true;
    while (invalidInput) {
      MessageCli.INSERT_COUNTRY.printMessage();
      input = Utils.scanner.nextLine();
      try {
        country = graph.getCountry(input);
      } catch (InvalidCountry e) {
        input = Utils.capitalizeFirstLetterOfEachWord(input);
        MessageCli.INVALID_COUNTRY.printMessage();
        continue;
      }
      invalidInput = false;
    }

    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(), country.getContinent(), Integer.toString(country.getTax()));
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
