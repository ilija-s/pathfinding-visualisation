package pathfindingVisualization;

public class AStar {


    private static class Node {
        private int x;
        private int y;
        private int f, g, h;
        private Node parent;

        public Node (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
