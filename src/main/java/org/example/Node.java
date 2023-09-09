package org.example;

/**
 * Represents a node in the A* Algorithm.
 * @author Jake Tammaro
 */
public class Node {
    /*
    The node that was traversed to get to this node.
     */
    public Node parent;
    /*
    Coordinates of the node in the board.
     */
    public int x, y;
    /*
    The cost to get to this node from the start.
     */
    public int costFromStart;
    /*
    The estimated cost to target node, calculated with Manhattan distance.
     */
    public int costToGoal;
    /*
    The total cost of the node, calculated by adding costFromStart and costToGoal.
     */
    public int totalCost;

    /**
     * Node constructor.
     * @author Jake Tammaro
     * @param x The x coordinate of the node.
     * @param y The y coordinate of the node.
     * @param parent The node that was traversed to get to this node.
     */
    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}
