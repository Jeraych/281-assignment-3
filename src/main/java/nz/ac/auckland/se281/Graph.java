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

  /**
   * Gets the country requested.
   *
   * @param name a string that is the name of the country needed.
   * @return the country requested.
   * @throws InvalidCountry when entering a country name that is not in the list of countries
   *     provided, or wrong capitalisation.
   */
  public Country getCountry(String name) throws InvalidCountry {
    name = Utils.capitalizeFirstLetterOfEachWord(name);
    for (Country country : adjCountries.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    throw new InvalidCountry(name);
  }

  /**
   * Using BFS to find the shortest desired path from one country to another on the map, by keeping
   * a list of all the paths from one country to its neighbours.
   *
   * @param root the country to departed from where no tax is paid.
   * @param destination the arrived country from the starting country.
   * @return a list containing the path of countries visited.
   */
  public List<Country> findBreathFirstTraversalPath(Country root, Country destination) {
    Set<Country> visited = new HashSet<>();
    Queue<Country> queue = new LinkedList<>();
    List<ParentMap> parentMap = new ArrayList<>();
    List<Country> path = new ArrayList<>();
    ParentMap parent;

    queue.add(root);
    visited.add(root);
    while (!queue.isEmpty()) {
      Country node = queue.poll();
      for (Country n : adjCountries.get(node)) {
        // parent map keeps every previous path with parents and child labelled
        parent = new ParentMap(node, n);
        parentMap.add(parent);
        if (n.equals(destination)) {
          path.add(n);
          while (!node.equals(root)) {
            // search till we reach the final parent (starting country)
            for (ParentMap i : parentMap) {
              // search for any combination where the child country was reached from another parent
              // country
              if (i.getChild().equals(node)) {
                // swap the order so we can look for the next parent of this current parent country
                path.add(node);
                node = i.getParent();
                break;
              }
            }
          }
          // reverse to get the order correct (starting country to destination country)
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
