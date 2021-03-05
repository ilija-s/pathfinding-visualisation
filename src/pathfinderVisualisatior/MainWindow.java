package pathfinderVisualisatior;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
    private Button btnStart;
    private Button btnReset;
    private Canvas canvas;
    private int width = 600;
    private int height = 600;
    private int gridCells = 30;
    private float cellSize = width/gridCells;
    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    private Affine affine;
    private int drawMode;
    private boolean bfsInstantiated = false;
    private Grid grid = new Grid(height/ gridCells, width/ gridCells);
    private Bfs bfs;

    public MainWindow() {
        HBox hbTop = new HBox(10);
        hbTop.setPadding(new Insets(5, 10, 5, 10));
        this.btnStart = new Button("Start");
        this.btnReset = new Button("Reset");
        hbTop.getChildren().addAll(btnStart, btnReset);
        this.canvas = new Canvas(width, height);
        this.canvas.setOnMousePressed(this::drawSquare);
        this.canvas.setOnMouseDragged(this::drawSquare);
        this.canvas.setOnKeyPressed(this::handlePressedKey);
        this.btnStart.setOnAction(this::solveWithBFS);
        this.btnReset.setOnAction(this::reset);

        this.affine = new Affine();
        this.affine.appendScale(width/ cellSize, height/ cellSize); // size of cell

        this.drawMode = 1;

        this.getChildren().addAll(hbTop, this.canvas);
    }

    private void reset(ActionEvent actionEvent) {
        this.startX = -1;
        this.startY = -1;
        this.endX = -1;
        this.endY = -1;
        grid.resetGrid();
        if (bfsInstantiated) {
            bfs.setGrid(this.grid);
            bfs.setStartX(this.startX);
            bfs.setStartY(this.startY);
            bfs.reset();
        }
        this.drawMode = 1;
    }

    private void solveWithBFS(ActionEvent actionEvent) {
        if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
            bfs = new Bfs(this, this.grid, this.startX, this.startY);
            bfsInstantiated = true;
            return;
        }
        System.out.println("Not valid start or end");
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
}
