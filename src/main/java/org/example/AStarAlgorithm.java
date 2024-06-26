package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Represents the A* Algorithm, used to generate enemy paths.
 */
public class AStarAlgorithm {
    private final Board map;
    private int width, height;

    public AStarAlgorithm(Board map) {
        this.map = map;
        if (map == null) return;
        this.width = map.getWidth();
        this.height = map.getHeight();

    }

    /**
     * Main method of the A* algorithm. Finds the cheapest path to the goal.
     * @param startX The starting X coordinate.
     * @param startY The starting Y coordinate.
     * @param goalX  The goal X coordinate.
     * @param goalY The goal Y coordinate.
     * @return A list of nodes representing the path to the goal.
     * @author Jake Tammaro
     */
    public List<Node> findPath(int startX, int startY, int goalX, int goalY) {
        // Initialize start and goal nodes
        Node startNode = new Node(startX, startY, null);
        Node goalNode = new Node(goalX, goalY, null);

        // Initialize open and closed lists
        // Open list is possible nodes to visit
        // Closed list is nodes that have already been visited

        // Priority queue is a queue that sorts by a given comparator, in this case the total cost of the node
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(node -> node.totalCost));
        ArrayList<Node> closedList = new ArrayList<>();

        openList.add(startNode);

        while (!openList.isEmpty()) {
            // Current node is the node with the lowest total cost
            Node currentNode = openList.poll();
            closedList.add(currentNode);

            if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
                return buildPath(currentNode);
            }

            // Construct nodes for each neighbor
            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(neighbor)) continue;

                double newCostFromStart = currentNode.costFromStart + 1;  // Cost of one to move in any direction

                if (openList.contains(neighbor) && newCostFromStart >= neighbor.costFromStart) continue;

                neighbor.parent = currentNode;
                neighbor.costFromStart = newCostFromStart;
                neighbor.costToGoal = Enemy.manhattanDistance(neighbor.x, neighbor.y, goalNode.x, goalNode.y);
                neighbor.totalCost = neighbor.costToGoal + neighbor.costFromStart;

                openList.add(neighbor);
            }
        }

        return null;  // no path found
    }

    /**
     * Builds a list of nodes representing the path to the goal.
     * Works by traversing the parent nodes of each node until the start node is reached.
     * @param node The goal node.
     * @return A list of nodes representing the path to the goal.
     * @author Jake Tammaro
     */
    public List<Node> buildPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node);
            node = node.parent;
        }
        return path;
    }

    /**
     * Gets the neighbors of a node.
     * @param node The node to get neighbors of.
     * @return A list of nodes representing the neighbors of the node.
     * @author Jake Tammaro
     */

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        // This checks the 4 neighboring tiles.
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] dir : dirs) {
            int x = node.x + dir[0];
            int y = node.y + dir[1];

            if (map.getTile(x,y) == Tile.Empty) {
                neighbors.add(new Node(x, y, node));
            }
        }

        return neighbors;
    }

    /**
     * Prints the path to the console.
     * @param board The board to print the path on.
     * @param path The path to print.
     * @author Jake Tammaro
     */
    public void printPath(Board board, List<Node> path) {
        if (path == null || path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        int height = board.getHeight();
        int width = board.getWidth();


        // Create a visual representation of the board with the path
        char[][] visualMap = new char[height][width];

        // Fill the visualMap with empty tiles representation
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visualMap[i][j] = (board.getTile(j,i) == Tile.Empty) ? '.' : '#';  // assuming # for non-empty tiles
            }
        }

        // Mark the path on the visualMap
        for (Node node : path) {
            visualMap[node.y][node.x] = '*';
        }

        // Mark start and end points
        visualMap[path.get(0).y][path.get(0).x] = 'S';
        visualMap[path.get(path.size() - 1).y][path.get(path.size() - 1).x] = 'E';

        // Print the visualMap
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(visualMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }




    /**
     * Represents a node in the A* Algorithm.
     * @author Jake Tammaro
     */
    public static class Node {
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
        public double costFromStart;
        /*
        The estimated cost to target node, calculated with Manhattan distance.
         */
        public double costToGoal;
        /*
        The total cost of the node, calculated by adding costFromStart and costToGoal.
         */
        public double totalCost;

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


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

    }
}
