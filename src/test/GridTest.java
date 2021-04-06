package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pathfindingVisualization.Grid;
import pathfindingVisualization.Node;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        this.grid = new Grid(5, 5);
        grid.setWall(0, 1);
        grid.setWall(1, 1);
        grid.setWall(1, 0);
    }

    @Test
    void getNeighbours_noValidNeighbours() {
        Node node = new Node(0, 0, null);
        List<Node> actualNeighbours = grid.getNeighbours(node);
        List<Node> expectedNeighbours = new LinkedList<>();

        assertArrayEquals(expectedNeighbours.toArray(), actualNeighbours.toArray());
    }

    @Test
    void getNeighbours_correctListOfNeighbours() {
        Node node = new Node(0, 2, null);
        List<Node> actualNeighbours = grid.getNeighbours(node);
        List<Node> expectedNeighbours = new LinkedList<>();
        expectedNeighbours.add(new Node(0, 3, null));
        expectedNeighbours.add(new Node(1, 2, null));
        expectedNeighbours.add(new Node(1, 3, null));

        assertArrayEquals(expectedNeighbours.toArray(), actualNeighbours.toArray());
    }

    @Test
    void getNeighbours_invalidGridPosition() {
        Node node = new Node(-1, -1, null);

        List<Node> actualNeighbours = grid.getNeighbours(node);
        List<Node> expectedNeighbours = new LinkedList<>();
        expectedNeighbours.add(new Node(0, 0, null));

        System.out.println(actualNeighbours);

        assertArrayEquals(expectedNeighbours.toArray(), actualNeighbours.toArray());
    }
}