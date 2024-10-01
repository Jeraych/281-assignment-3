# Assignment 3

Do you know this map?

![risk_game_board](https://github.com/user-attachments/assets/fbd32c64-48e9-4bd1-b6c1-e9b7241bc65a)

Is the Map of the Game Risk! a strategy board game where you fight your opponents with dice to win their countries. Don’t worry, I am not asking you to implement the game; we will just use the map. I’m not sure how famous this game is in New Zealand, as New Zealand is obviously missing from the map. Apparently, it is a common trend to omit our beautiful country. Maps Without NZ.

Anyway, this map is a simplified version of the World map with only 42 countries and 6 continents: Europe, North America, South America, Africa, Asia, and Australia. You can see that there are gray lines to show which countries are connected via water.

A Graph is the perfect data structure to model a map. In this case, countries are the nodes (vertices) of the graph. The graph will have 42 nodes. The edges in the map represent the “neighborhood” relation. For example, Brazil has Venezuela, North Africa, Argentina, and Peru as neighboring countries. Thus, Brazil will have an edge to the nodes of the graph that represent those countries. For example, there is no edge between India and Siberia because they are not adjacent countries (do not share a border or have a link via water). This relation is clearly symmetric: if country A is a neighbor of country B, then country B must also be a neighbor of country A.

By traversing the resulting Graph, we can simulate routing across countries. For example, to go from Congo to Brazil, a possible path is Congo -> North Africa -> Brazil. We cannot jump from Congo to Brazil because these two countries are not adjacent; we must go through North Africa.

What you need to implement for A3 is the code to load the map into a graph and print the shortest path between two countries.

Additionally, you can notice that in the Risk map, each country has an associated number. In the Risk game, these numbers are used for reinforcement bonuses. In this assignment, we are using those numbers to specify the taxes that need to be paid to cross the border. For example, the total taxes to pay from Congo to Brazil are: 5 (for North Africa) and 2 (for Brazil), totaling 7 NZD. We don’t need to pay the 1 NZD for Congo because we start from there.

This assignment simulates a real case scenario where a company offering international delivery wants to implement software to find the optimal routing across the world. The map would be more complex, but they will definitely use a Graph data structure.
