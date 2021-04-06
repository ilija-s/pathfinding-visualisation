package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pathfindingVisualization.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {

    private Node startNode;
    private Node endNode;

    @BeforeEach
    void setUp() {
        startNode = new Node(1, 1, null);
        endNode = new Node(5, 5, null);
    }

    @Test
    void setF_returnsCorrectDistanceFromStart() {
        Node node1 = new Node(4, 1, null);
        Node node2 = new Node(6, 3, null);
        int actualDistance1 = Node.getDistance(node1, startNode);
        int actualDistance2 = Node.getDistance(node2, startNode);
        int expectedDistance1 = 3 * 10;
        int expectedDistance2 = 3 * 10 + 2 * 14;

        assertEquals(expectedDistance1, actualDistance1);
        assertEquals(expectedDistance2, actualDistance2);
    }

    @Test
    void setF_returnsCorrectDistanceFromEnd() {
        Node node1 = new Node(0, 0, null);
        Node node2 = new Node(2, 3, null);
        int actualDistance1 = Node.getDistance(node1, endNode);
        int actualDistance2 = Node.getDistance(node2, endNode);
        int expectedDistance1 = 5 * 14;
        int expectedDistance2 = 2 * 14 + 10;

        assertEquals(expectedDistance1, actualDistance1);
        assertEquals(expectedDistance2, actualDistance2);
    }
}
