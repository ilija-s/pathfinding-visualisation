package pathfinderVisualisatior;

import java.util.LinkedList;
import java.util.Random;

public class MazeGenerator {
    public static final boolean WALL = false;
    public static final boolean PATH = true;

    private Grid grid;
    private final int width;
    private final int height;

    public MazeGenerator(Grid grid, int startX, int startY, int endX, int endY){
        this.grid = grid;
        this.width = grid.getCols();
        this.height = grid.getRows();
        this.grid = fillWithWalls();

        final LinkedList<int[]> frontiers = new LinkedList<>();
        final Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        frontiers.add(new int[]{x,y,x,y});

        while (!frontiers.isEmpty() ){
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
            }
        }

        grid.setStart(startX, startY);
        grid.setEnd(endX, endY);
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
