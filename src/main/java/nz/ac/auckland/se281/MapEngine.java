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

  /**
   * Use Utils scanner to ask for users input of country.
   *
   * @param message a specific MessageCli that is used to display error message.
   */
  public void askInput(MessageCli message) {
    // ask for country and check if it exists repeatively
    invalidInput = true;
    while (invalidInput) {
      message.printMessage();
      input = Utils.scanner.nextLine();
      try {
        country = graph.getCountry(input);
      } catch (InvalidCountry e) {
        MessageCli.INVALID_COUNTRY.printMessage(Utils.capitalizeFirstLetterOfEachWord(input));
        continue;
      }
      invalidInput = false;
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
    askInput(MessageCli.INSERT_COUNTRY);

    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(), country.getContinent(), Integer.toString(country.getTax()));
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    Country source;
    Country destination;
    List<String> continents;
    int tax = 0;
    askInput(MessageCli.INSERT_SOURCE);
    source = country;
    askInput(MessageCli.INSERT_DESTINATION);
    destination = country;

    if (source.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // calculate best path
    countryList = graph.findBreathFirstTraversalPath(source, destination);
    MessageCli.ROUTE_INFO.printMessage(countryList.toString());

    // calculate continents travelled
    continents = new ArrayList<>();
    for (Country i : countryList) {
      if (!continents.contains(i.getContinent())) {
        continents.add(i.getContinent());
      }
    }
    MessageCli.CONTINENT_INFO.printMessage(continents.toString());

    // calculate taxes needed
    for (Country i : countryList) {
      tax += i.getTax();
    }
    tax -= source.getTax();
    MessageCli.TAX_INFO.printMessage(Integer.toString(tax));
  }
}
