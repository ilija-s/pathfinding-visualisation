package pathfinderVisualisatior;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Random;

public class MazeGenerator {
    public static final boolean WALL = false;
    public static final boolean PATH = true;

    private Grid grid;
    private final int width;
    private final int height;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    private Timeline timeline;

    final LinkedList<int[]> frontiers = new LinkedList<>();
    final Random random = new Random();
    int x;
    int y;

    MainWindow mainWindow;

    public MazeGenerator(MainWindow mainWindow, Grid grid, int startX, int startY, int endX, int endY){
        this.mainWindow = mainWindow;
        this.grid = grid;
        this.width = grid.getCols();
        this.height = grid.getRows();
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.grid = fillWithWalls();

        this.x = random.nextInt(width);
        this.y = random.nextInt(height);
        frontiers.add(new int[]{x,y,x,y});

        startMazeGeneratorTimeline();
    }

    private void startMazeGeneratorTimeline() {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(20),  this::generate));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    private void generate(ActionEvent actionEvent) {
        if (!frontiers.isEmpty() ){
            final int[] f = frontiers.remove(random.nextInt(frontiers.size() ) );
            x = f[2];
            y = f[3];
            if (grid.isWall(x, y)) {
                this.grid.setPath(x, y); this.grid.setPath(f[0], f[1]);
                if (x >= 2 && grid.isWall(x - 2, y))
                    frontiers.add(new int[]{x-1,y,x-2,y} );
                if (y >= 2 && grid.isWall(x, y - 2) )
                    frontiers.add(new int[]{x,y-1,x,y-2} );
                if (x < width - 2 && grid.isWall(x + 2, y))
                    frontiers.add(new int[]{x+1,y,x+2,y} );
                if (y < height - 2 && grid.isWall(x, y + 2))
                    frontiers.add(new int[]{x,y+1,x,y+2} );
                mainWindow.draw();
            }
        } else {
            grid.setStart(startX, startY);
            grid.setEnd(endX, endY);
            mainWindow.draw();
            this.timeline.setCycleCount(0);
            this.timeline.stop();
        }
    }

    private Grid fillWithWalls() {
       for (int i = 0; i < this.height; i++) {
           for (int j = 0; j < this.width; j++) {
               grid.setWall(i, j);
           }
       }
       return grid;
    }

    public Grid getGrid() {
        return this.grid;
    }
}
