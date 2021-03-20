package pathfindingVisualization;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainWindow extends VBox {
    private static final int PATH = 0;
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int FINISH = 3;
    private static final int VISITED = 4;
    private static final int SHORTESTPATH = 5;
    private static final int CLOSED = 6;
    private Button btnBFS;
    private Button btnDFS;
    private Button btnReset;
    private Button btnGenerateMaze;
    private Label lblPathNotFound;
    private Canvas canvas;
    private int width = 900;
    private int height = 900;
    private int gridCells = 30;
    private float cellSize = (float) width / gridCells;
    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    private Affine affine;
    private int drawMode;
    private boolean bfsInstantiated = false;
    private boolean dfsInstantiated = false;
    private Grid grid = new Grid(height/ gridCells, width/ gridCells);
    private Bfs bfs;
    private Dfs dfs;
    private boolean diagonalSearch = true;

    public MainWindow() {
        HBox hbTop = new HBox(10);
        hbTop.setPadding(new Insets(5, 10, 5, 10));

        this.btnBFS = new Button("BFS");
        this.btnDFS = new Button("DFS");
        this.btnReset = new Button("Reset");
        this.btnGenerateMaze = new Button("Generate Maze");
        this.lblPathNotFound = new Label("Path not found!");
        this.lblPathNotFound.setVisible(false);
        hbTop.getChildren().addAll(btnBFS, btnDFS, btnGenerateMaze, btnReset, lblPathNotFound);

        this.canvas = new Canvas(width, height);
        this.btnDFS.setOnAction(this::solveWithDFS);
        this.btnBFS.setOnAction(this::solveWithBFS);
        this.btnGenerateMaze.setOnAction(this::generateMaze);
        this.btnReset.setOnAction(this::reset);
        this.canvas.setOnMousePressed(this::drawSquare);
        this.canvas.setOnMouseDragged(this::drawSquare);
        this.canvas.setOnKeyPressed(this::handlePressedKey);

        this.affine = new Affine();
        this.affine.appendScale(width/ cellSize, height/ cellSize); // size of cell

        this.drawMode = 1;

        this.getChildren().addAll(hbTop, this.canvas);
    }

    private void generateMaze(ActionEvent actionEvent) {
        disableAllButtons();
        MazeGenerator mazeGenerator = new MazeGenerator(this, this.grid, this.startX, this.startY, this.endX, this.endY);
        this.grid = mazeGenerator.getGrid();
        enableAllButtons();
        draw();
    }

    private void reset(ActionEvent actionEvent) {
        disableAllButtons();
        this.lblPathNotFound.setVisible(false);
        grid.resetGrid();
        if (bfsInstantiated) {
            bfs.setGrid(this.grid);
            bfs.setStartX(this.startX);
            bfs.setStartY(this.startY);
            bfs.reset();
        }
        else if (dfsInstantiated) {
            dfs.setGrid(this.grid);
            dfs.setStartX(this.startX);
            dfs.setStartY(this.startY);
            dfs.reset();
        }
        this.drawMode = 1;
        enableAllButtons();
    }

    private void enableAllButtons() {
        this.btnBFS.setDisable(false);
        this.btnDFS.setDisable(false);
        this.btnGenerateMaze.setDisable(false);
        this.btnReset.setDisable(false);
    }

    private void disableAllButtons() {
        this.btnBFS.setDisable(true);
        this.btnDFS.setDisable(true);
        this.btnGenerateMaze.setDisable(true);
        this.btnReset.setDisable(true);
    }

    private void solveWithBFS(ActionEvent actionEvent) {
        if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
            bfs = new Bfs(this, this.grid, this.startX, this.startY, diagonalSearch);
            bfsInstantiated = true;
            bfs.startSearchTimeline();
            return;
        }
        this.lblPathNotFound.setVisible(true);
    }

    private void solveWithDFS(ActionEvent actionEvent) {
        if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
            dfs = new Dfs(this, this.grid, this.startX, this.startY, diagonalSearch);
            dfsInstantiated = true;
            dfs.startSearchTimeline();
            return;
        }
        this.lblPathNotFound.setVisible(true);
    }

    private void handlePressedKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.drawMode = WALL;
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.drawMode = PATH;
        } else if (keyEvent.getCode() == KeyCode.S) {
            this.drawMode = START;
        } else if (keyEvent.getCode() == KeyCode.F) {
            this.drawMode = FINISH;
        }
    }

    private void drawSquare(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        try {
            Point2D point = this.affine.inverseTransform(x,y);
            int pointX = (int) point.getX();
            int pointY = (int) point.getY();

            if (drawMode == 0) {
                this.grid.setPath(pointY, pointX);
            } else if (drawMode == 1) {
                this.grid.setWall(pointY, pointX);
            } else if (drawMode == 2) {
                if (startX != -1 && startY != -1) {
                    this.grid.setPath(startX, startY);
                }
                this.startX = pointY;
                this.startY = pointX;
                this.grid.setStart(startX, startY);
            } else if (drawMode == 3) {
                if (endX != -1 && endY != -1) {
                    this.grid.setPath(endX, endY);
                }
                this.endX = pointY;
                this.endY = pointX;
                this.grid.setEnd(endX, endY);
            }
            draw();
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        GraphicsContext graphics = this.canvas.getGraphicsContext2D();
        graphics.setTransform(this.affine);

        graphics.setFill(Color.LIGHTGRAY);
        graphics.fillRect(0, 0, width, height);

        for (int x = 0; x < height/ gridCells; x++) {
            for (int y = 0; y < width/ gridCells; y++) {
                int cellValue = grid.getCellValue(x, y);
                drawRect(x, y, cellValue);
            }
        }

        /* draws grid lines */
        graphics.setStroke(Color.GRAY);
        graphics.setLineWidth(0.05f);
        for (int x = 0; x <= height / gridCells; x++) {
            graphics.strokeLine(x,0, x, cellSize);
        }
        for (int y = 0; y <= width / gridCells; y++) {
            graphics.strokeLine(0, y, cellSize, y);
        }
    }

    private void drawRect(int x, int y, final int cellValue) {
        GraphicsContext graphics = this.canvas.getGraphicsContext2D();
        graphics.setTransform(this.affine);

        switch (cellValue) {
            case WALL -> {
                graphics.setFill(Color.BLACK);
                graphics.fillRect(y, x, 1, 1);
            }
            case PATH -> {
                graphics.setFill(Color.WHITE);
                graphics.fillRect(y, x, 1, 1);
            }
            case START -> {
                graphics.setFill(Color.FORESTGREEN);
                graphics.fillRect(y, x, 1, 1);
            }
            case FINISH -> {
                graphics.setFill(Color.ORANGERED);
                graphics.fillRect(y, x, 1, 1);
            }
            case VISITED -> {
                graphics.setFill(Color.MEDIUMBLUE);
                graphics.fillRect(y, x, 1, 1);
            }
            case SHORTESTPATH -> {
                graphics.setFill(Color.YELLOW);
                graphics.fillRect(y, x, 1, 1);
            }

            case CLOSED -> {
                graphics.setFill(Color.BLUEVIOLET);
                graphics.fillRect(y, x, 1, 1);
            }
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Label getLblPathNotFound() {
        return lblPathNotFound;
    }
}
