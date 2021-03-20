package pathfindingVisualization;

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
    private boolean diagonalSearch;
    private boolean[][] visited;
    private Deque<Node> nodes = new LinkedList<>();
    private Node endNode;

    public Bfs(MainWindow mainWindow, Grid grid, int startX, int startY, boolean diagonalSearch) {
        this.visited = new boolean[grid.getRows()][grid.getCols()];
        this.grid = grid;
        this.endNode = new Node();
        this.startX = startX;
        this.startY = startY;
        this.diagonalSearch = diagonalSearch;
        this.mainWindow = mainWindow;
        nodes.addFirst(new Node(startX, startY, null));
        visited[startX][startY] = true;
    }

    public void startSearchTimeline() {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(20), this::search));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    private void search(ActionEvent actionEvent) {
        int retval;
        if (diagonalSearch)
            retval = this.searchWithDiagonals();
        else
            retval = this.search();
        if (retval == NOPATH) {
            this.mainWindow.getLblPathNotFound().setVisible(true);
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

    public int search() {
        Node tmp;

        if (!nodes.isEmpty()) {
            tmp = nodes.getLast();
            nodes.removeLast();
            if (!grid.isStart(tmp.getX(), tmp.getY()))
                grid.setClosed(tmp.getX(), tmp.getY());

            if (grid.isEnd(tmp.getX(), tmp.getY())) {
                endNode = tmp;
                return END;
            }

            if (allowed(tmp.getX() - 1, tmp.getY())) {
                nodes.addFirst(new Node(tmp.getX() - 1, tmp.getY(), tmp));
                visited[endNode.getX() - 1][tmp.getY()] = true;
                if (grid.isEnd(tmp.getX() - 1, tmp.getY())) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() - 1, tmp.getY());
            }
            if (allowed(tmp.getX() + 1, tmp.getY())) {
                nodes.addFirst(new Node(tmp.getX() + 1, tmp.getY(), tmp));
                visited[tmp.getX() + 1][tmp.getY()] = true;
                if (grid.isEnd(tmp.getX() + 1, tmp.getY())) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() + 1, tmp.getY());
            }
            if (allowed(tmp.getX(), tmp.getY() - 1)) {
                nodes.addFirst(new Node(tmp.getX(), tmp.getY() - 1, tmp));
                visited[tmp.getX()][tmp.getY() - 1] = true;
                if (grid.isEnd(tmp.getX(), tmp.getY() - 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX(), tmp.getY() - 1);
            }
            if (allowed(tmp.getX(), tmp.getY() + 1)) {
                nodes.addFirst(new Node(tmp.getX(), tmp.getY() + 1, tmp));
                visited[tmp.getX()][tmp.getY() + 1] = true;
                if (grid.isEnd(tmp.getX(), tmp.getY() + 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX(), tmp.getY() + 1);
            }
        } else {
            return NOPATH;
        }
        return 1;
    }

    public int searchWithDiagonals() {
        Node tmp;

        if (!nodes.isEmpty()) {
            tmp = nodes.getLast();
            nodes.removeLast();
            if (!grid.isStart(tmp.getX(), tmp.getY()))
                grid.setClosed(tmp.getX(), tmp.getY());

            if (grid.isEnd(tmp.getX(), tmp.getY())) {
                endNode = tmp;
                return END;
            }

            if (allowed(tmp.getX() - 1, tmp.getY())) {
                nodes.addFirst(new Node(tmp.getX() - 1, tmp.getY(), tmp));
                visited[tmp.getX() - 1][tmp.getY()] = true;
                if (grid.isEnd(tmp.getX() - 1, tmp.getY())) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() - 1, tmp.getY());
            }
            if (allowed(tmp.getX() + 1, tmp.getY())) {
                nodes.addFirst(new Node(tmp.getX() + 1, tmp.getY(), tmp));
                visited[tmp.getX() + 1][tmp.getY()] = true;
                if (grid.isEnd(tmp.getX() + 1, tmp.getY())) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() + 1, tmp.getY());
            }
            if (allowed(tmp.getX(), tmp.getY() - 1)) {
                nodes.addFirst(new Node(tmp.getX(), tmp.getY() - 1, tmp));
                visited[tmp.getX()][tmp.getY() - 1] = true;
                if (grid.isEnd(tmp.getX(), tmp.getY() - 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX(), tmp.getY() - 1);
            }
            if (allowed(tmp.getX(), tmp.getY() + 1)) {
                nodes.addFirst(new Node(tmp.getX(), tmp.getY() + 1, tmp));
                visited[tmp.getX()][tmp.getY() + 1] = true;
                if (grid.isEnd(tmp.getX(), tmp.getY() + 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX(), tmp.getY() + 1);
            }
            if (allowed(tmp.getX() - 1, tmp.getY() - 1)) {
                nodes.addFirst(new Node(tmp.getX() - 1, tmp.getY() - 1, tmp));
                visited[tmp.getX() - 1][tmp.getY() - 1] = true;
                if (grid.isEnd(tmp.getX() - 1, tmp.getY() - 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() - 1, tmp.getY() - 1);
            }
            if (allowed(tmp.getX() + 1, tmp.getY() + 1)) {
                nodes.addFirst(new Node(tmp.getX() + 1, tmp.getY() + 1, tmp));
                visited[tmp.getX() + 1][tmp.getY() + 1] = true;
                if (grid.isEnd(tmp.getX() + 1, tmp.getY() + 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() + 1, tmp.getY() + 1);
            }
            if (allowed(tmp.getX() - 1, tmp.getY() + 1)) {
                nodes.addFirst(new Node(tmp.getX() - 1, tmp.getY() + 1, tmp));
                visited[tmp.getX() - 1][tmp.getY() + 1] = true;
                if (grid.isEnd(tmp.getX() - 1, tmp.getY() + 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() - 1, tmp.getY() + 1);
            }
            if (allowed(tmp.getX() + 1, tmp.getY() - 1)) {
                nodes.addFirst(new Node(tmp.getX() + 1, tmp.getY() - 1, tmp));
                visited[tmp.getX() + 1][tmp.getY() - 1] = true;
                if (grid.isEnd(tmp.getX() + 1, tmp.getY() - 1)) {
                    endNode = nodes.getFirst();
                    return END;
                }
                grid.setVisited(tmp.getX() + 1, tmp.getY() - 1);
            }
        } else {
            return NOPATH;
        }
        return 1;
    }

    private void backtrackPath() {
        Node tmp;
        tmp = endNode;
        while (tmp.getParent() != null) {
            tmp = tmp.getParent();
            if (!grid.isStart(tmp.getX(), tmp.getY()))
                grid.setShortestPath(tmp.getX(), tmp.getY());
        }
    }

    public void reset() {
        this.startX = -1;
        this.startY = -1;
        this.nodes = new LinkedList<>();
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
