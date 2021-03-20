package pathfindingVisualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

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
    private boolean endFound = false;

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
        if (endFound) {
            if (endNode.getParent() != null) {
                if (endNode.getX() == startNode.getX() && endNode.getY() == startNode.getY()) {
                    grid.setStart(startNode.getX(), startNode.getY());
                    return 1;
                }
                endNode = endNode.getParent();
                grid.setShortestPath(endNode.getX(), endNode.getY());
                grid.setStart(startNode.getX(), startNode.getY());
                return -1;
            } else {
                return 1;
            }
        } else if (!open.isEmpty()) {
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
            grid.setClosed(current.getX(), current.getY());
            grid.setStart(startNode.getX(), startNode.getY());
            grid.setEnd(endNode.getX(), endNode.getY());

            if (current.getX() == endNode.getX() && current.getY() == endNode.getY()) {
                endNode.setParent(current.getParent());
                endFound = true;
                return -1;
            }

            for (Node node : grid.getNeighbours(current)) {
                if (closed.contains(node)) {
                    continue;
                }
                int newCostToNeighbour = current.getG() + getDistance(current, node);
                if (newCostToNeighbour < node.getG() || !open.contains(node)) {
                    node.setG(newCostToNeighbour);
                    node.setH(getDistance(node, endNode));
                    node.setParent(current);

                    if (!open.contains(node)) {
                        grid.setVisited(node.getX(), node.getY());
                        open.add(node);
                    }
                }
            }
            grid.setEnd(endNode.getX(), endNode.getY());
            return -1;
        } else {
            return 0;
        }
    }

    public void startAStarSearch(ActionEvent a) {
        int retval = search();
        if (retval == 1) {
            stopTimeline();
        } else if (retval == 0){
            this.mainWindow.getLblPathNotFound().setVisible(true);
            stopTimeline();
        }

        this.mainWindow.draw();
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
        setStart(-1, -1);
        mainWindow.draw();
    }

    public void startSearchTimeline() {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(30), this::startAStarSearch));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    private void stopTimeline() {
        this.timeline.setCycleCount(0);
        this.timeline.stop();
    }
}
