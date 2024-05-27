package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

  public List<Country> breathFirstTraversal(Country root) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    queue.add(root);
    visited.add(root);
    while (!queue.isEmpty()) {
      Country node = queue.poll();
      for (Country n : adjCountries.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
        }
      }
    }
    return visited;
  }

  public List<Country> shortestRoute(Country source, Country destination) {
    List<Country> route = new ArrayList<>();
    for (Country i : breathFirstTraversal(source)) {
      route.add(i);
      if (i.equals(destination)) {
        return route;
      }
    }
    return route;
  }
}
