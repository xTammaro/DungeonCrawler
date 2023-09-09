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
}
