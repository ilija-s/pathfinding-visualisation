package pathfindingVisualization;

import java.util.*;

public class Node {
    private int x;
    private int y;
    private int f, g, h;
    Node parent;

    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public Node(int x, int y, int f, int g, int h, Node parent) {
        this.x = x;
        this.y = y;
        this.f = f;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    public Node() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public Node getParent() {
        return parent;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return this.getX() == node.getX() &&
                this.getY() == node.getY() &&
                this.getParent() == node.getParent();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
