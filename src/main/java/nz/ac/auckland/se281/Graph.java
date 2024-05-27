package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

  public List<Country> breathFirstTraversal(Country root, Country destination) {
    Set<Country> visited = new HashSet<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> parentMap = new HashMap<>();
    List<Country> path = new ArrayList<>();
    queue.add(root);
    visited.add(root);
    while (!queue.isEmpty()) {
        Country node = queue.poll();
        for (Country n : adjCountries.get(node)) {
          parentMap.put(node, n);
          if (n.equals(destination)) {
            path.add(n);
            while (!node.equals(root)) {
              for (Country i : parentMap.keySet()) {
                if (i.equals(node)) {
                  path.add(node);
                  n = node;
                  break;
                }
              }
            }
            path.add(node);
            Collections.reverse(path);
            return path;
          }
            if (!visited.contains(n)) {
                visited.add(n);
                queue.add(n);
            }
        }
    }
    return null;
  }
}
