Pathfinding Visualization
-------------------------

Pathfinding visualizer built in Java using JavaFX library for GUI. Each cell on a grid has its color value which determines its purpose.

Colors
------

* White represents all available "path" cells
* Black represents wall cells
* Green represents start cell
* Red represents end cell
* Blue represents all the cells that are visited and not yet closed
* Purple represents all the visited cells that are closed
* Yellow represents one of possibly more shortest paths

Description
-----------

For now this app allows user to choose between few implemented pathfinding algorithms like:
* BFS (breadth-first search) guarantees the shortest path, it is a pretty good algorithm for shortest paths on unweighted graphs
* DFS (depth first search) which does not guarantee the shortest path and is probably the worst one 
* A* algorithm is one of the best if not the best pathfinding algorithm, it uses heuristics to find the shortest path faster than Dijkstra's algorithm

There is also one Maze generation algorithm, here I've implemented Prim's maze generation algorithm.

Commands
--------

* To select start point press *s* key and select cell
* To select end point press *f* key and select end cell
* To delete use *e* and for drawing walls use *d*, select algorithm and press start

![basiccommands (1)](https://user-images.githubusercontent.com/46342896/111890493-0e49c400-89ea-11eb-9a1b-c7d13f108847.gif)

Maze generation
---------------

![mazegeneration](https://user-images.githubusercontent.com/46342896/111890466-c3c84780-89e9-11eb-9846-c42b81a5ed77.gif)

Breadth-First Search
--------------------

![bfs](https://user-images.githubusercontent.com/46342896/111890441-abf0c380-89e9-11eb-994f-3340eefd87a1.gif)

Run
---

To run program you will need to have JavaFX 11 downloaded (for Java versions above 8) and Java 15  
In Project Structure -> Libraries add path to JavaFX lib folder  
And finally go to Run -> Edit Configurations and in Program arguments add this line

```
--module-path /home/PATH_TO_JAVAFX/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml
```
