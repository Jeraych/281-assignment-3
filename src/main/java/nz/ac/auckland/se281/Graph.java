package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<Country, List<Country>> adjCountries;

  public Graph() {
    this.adjCountries = new HashMap<>();
  }

  public void addNode(Country node) {
    adjCountries.putIfAbsent(node, new ArrayList<>());
  }

  public void addEdge(Country node1, Country node2) {
    addNode(node1);
    adjCountries.get(node1).add(node2);
  }

  public Country getCountry(String name) throws InvalidCountry {
    name = Utils.capitalizeFirstLetterOfEachWord(name);
    for (Country country : adjCountries.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    throw new InvalidCountry(name);
  }
}
