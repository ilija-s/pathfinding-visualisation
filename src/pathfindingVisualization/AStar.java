package pathfindingVisualization;

import javafx.animation.Timeline;

import java.util.*;

public class AStar {
    private Timeline timeline;
    private MainWindow mainWindow;
    private Grid grid;
    private boolean[][] visited;
    private Node startNode;
    private Node endNode;
    private List<Node> open;
    private Set<Node> closed;

    public AStar(MainWindow mainWindow, Grid grid, int startX, int startY, int endX, int endY) {
        this.mainWindow = mainWindow;
        this.grid = grid;
        startNode = new Node(startX, startY, null);
        endNode = new Node(endX, endY, null);
        open = new LinkedList<>();
        closed = new HashSet<>();
        open.add(startNode);
        visited = new boolean[grid.getRows()][grid.getCols()];
        visited[startX][startY] = true;
    }

    private int search() {
        while (!open.isEmpty()) {
            Node current = open.get(0);
            for (int i = 1; i < open.size(); i++) {
                Node firstOpen = open.get(i);
                if (firstOpen.getF() < current.getF() ||
                        firstOpen.getF() == current.getF() && firstOpen.getH() < current.getH()) {
                    current = firstOpen;
                }
            }
            open.remove(current);
            closed.add(current);

            if (current.getX() == endNode.getX() && current.getY() == endNode.getY()) {
                endNode.setParent(current.getParent());
                return 1;
            }

            for (Node node : grid.getNeighbours(current)) {
                if (closed.contains(node) || visited[node.getX()][node.getY()]) {
                    continue;
                }
                visited[node.getX()][node.getY()] = true;
                int newCostToNeighbour = current.getG() + getDistance(current, node);
                if (newCostToNeighbour < node.getG() || !open.contains(node)) {
                    node.setG(newCostToNeighbour);
                    node.setH(getDistance(node, endNode));
                    node.setParent(current);

                    if (!open.contains(node)) {
                        open.add(node);
                    }
                }
            }
        }
        return 0;
    }

    public void startAStarSearch() {
        int retval = search();
        if (retval == 1) {
            System.out.println("Path found!");
        } else {
            System.out.println("Path not found!");
        }
    }

    private int getDistance(Node nodeA, Node nodeB) {
        int distX = Math.abs(nodeA.getX() - nodeB.getX());
        int distY = Math.abs(nodeA.getY() - nodeB.getY());

        if (distX > distY) {
            return 14 * distY + 10 * (distX - distY);
        }
        return 14 * distX + 10 * (distY - distX);
    }

    public void setStart(int x, int y) {
        this.startNode.setX(x);
        this.startNode.setY(y);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void reset() {
        visited = new boolean[grid.getRows()][grid.getCols()];
    }
}
