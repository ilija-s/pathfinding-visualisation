package pathfinderVisualisatior;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import java.util.Deque;
import java.util.LinkedList;

public class Bfs {
    private static final int END = 2;
    private static final int NOPATH = 0;
    private Timeline timeline;
    private MainWindow mainWindow;
    private Grid grid;
    private int startX;
    private int startY;
    private boolean[][] visited;
    private Deque<Node> nodes = new LinkedList<>();
    private Node endNode;

    public Bfs(MainWindow mainWindow, Grid grid, int startX, int startY) {
        this.visited = new boolean[grid.getRows()][grid.getCols()];
        this.grid = grid;
        this.startX = startX;
        this.startY = startY;
        this.mainWindow = mainWindow;
        nodes.addFirst(new Node(startX, startY, null));
        visited[startX][startY] = true;
        initTimeline();
    }

    private void initTimeline() {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(20), this::nextStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    private void nextStep(ActionEvent actionEvent) {
        int retval = this.nextStep();
        if (retval == NOPATH) {
            System.out.println("Path not found!");
            stopTimeline();
        } else if (retval == END) {
            stopTimeline();
            backtrackPath();
        }
        this.mainWindow.draw();
    }

    private void stopTimeline() {
        this.timeline.setCycleCount(0);
        this.timeline.stop();
    }

    private static class Node {
        private int x;
        private int y;
        Node parent;

        public Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    public int nextStep() {
        Node tmp;

        if (!nodes.isEmpty()) {
            tmp = nodes.getLast();
            nodes.removeLast();

            if (grid.isEnd(tmp.x, tmp.y)) {
                endNode = tmp;
                return END;
            }

            if (allowed(tmp.x - 1, tmp.y)) {
                nodes.addFirst(new Node(tmp.x - 1, tmp.y, tmp));
                visited[tmp.x - 1][tmp.y] = true;
                if (grid.isEnd(tmp.x - 1, tmp.y)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.x - 1, tmp.y);
            }
            if (allowed(tmp.x + 1, tmp.y)) {
                nodes.addFirst(new Node(tmp.x + 1, tmp.y, tmp));
                visited[tmp.x + 1][tmp.y] = true;
                if (grid.isEnd(tmp.x + 1, tmp.y)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.x + 1, tmp.y);
            }
            if (allowed(tmp.x, tmp.y - 1)) {
                nodes.addFirst(new Node(tmp.x, tmp.y - 1, tmp));
                visited[tmp.x][tmp.y - 1] = true;
                if (grid.isEnd(tmp.x, tmp.y - 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.x, tmp.y - 1);
            }
            if (allowed(tmp.x, tmp.y + 1)) {
                nodes.addFirst(new Node(tmp.x, tmp.y + 1, tmp));
                visited[tmp.x][tmp.y + 1] = true;
                if (grid.isEnd(tmp.x, tmp.y + 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.x, tmp.y + 1);
            }
        } else {
            return NOPATH;
        }
        return 1;
    }

    private void backtrackPath() {
        Node tmp;
        tmp = endNode;
        while (tmp.parent != null) {
            tmp = tmp.parent;
            if (!grid.isStart(tmp.x, tmp.y))
                grid.setShortestPath(tmp.x, tmp.y);
        }
    }

    public void reset() {
        this.startX = -1;
        this.startY = -1;
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                visited[i][j] = false;
            }
        }
        mainWindow.draw();
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    private boolean allowed(int x, int y) {
        return grid.isValid(x, y) && (grid.isPath(x, y) || grid.isEnd(x, y)) && !visited[x][y];
    }
}
