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

For now this app allows user to choose between few implemented pathfinding algorithms like BFS (breadth-first search), DFS (depth first search) and Dijksra's algorithm.

Usage
-----

* To select start point press "s" key and select cell
* To select end point press "f"
* To delete use "e" and for drawing walls use "d", select algorithm and press start

Run
---

To run program you will need to have JavaFX downloaded (for Java versions above 8)  
In Project Structure -> Libraries add path to JavaFX lib folder
And finally go to Run -> Edit Configurations and in Program arguments add this line

```
--module-path /home/PATH_TO_JAVAFX/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml
```
