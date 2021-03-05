package pathfindingVisualization;

public class Grid {
    public static final int PATH = 0;
    public static final int WALL = 1;
    public static final int START = 2;
    public static final int END = 3;
    public static final int VISITED = 4;
    public static final int SHORTESTPATH = 5;
    public static final int CLOSED = 6;
    private int[][] grid;
    private int rows;
    private int cols;

    public Grid(int rows, int cols) {
        this.grid = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setPath(int row, int col) {
        setState(row, col, PATH);
    }

    public void setWall(int row, int col) {
        setState(row, col, WALL);
    }

    public void setStart(int row, int col) {
        setState(row, col, START);
    }

    public void setEnd(int row, int col) {
        setState(row, col, END);
    }

    public void setVisited(int row, int col) {
        setState(row, col, VISITED);
    }

    public void setShortestPath(int row, int col) {
        setState(row, col, SHORTESTPATH);
    }

    public void setClosed(int row, int col) {
        setState(row, col, CLOSED);
    }

    public void setState(int row, int col, int state) {
        if (isValid(row, col)) {
            this.grid[row][col] = state;
        }
    }

    public boolean isPath(int row, int col) {
        return grid[row][col] == PATH;
    }

    public boolean isWall(int row, int col) {
        return grid[row][col] == WALL;
    }

    public boolean isStart(int row, int col) {
        return grid[row][col] == START;
    }

    public boolean isEnd(int row, int col) {
        return grid[row][col] == END;
    }

    public boolean isVisited(int row, int col) {
        return grid[row][col] == VISITED;
    }

    public boolean isShortestPath(int row, int col) {
        return grid[row][col] == SHORTESTPATH;
    }

    public boolean isClosed(int row, int col) {
        return grid[row][col] == CLOSED;
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < this.rows && y >= 0 && y < this.cols;
    }

    public void resetGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != START && grid[i][j] != END)
                    grid[i][j] = 0;
            }
        }
    }

    public int getCellValue(int x, int y) {
        return grid[x][y];
    }

}
