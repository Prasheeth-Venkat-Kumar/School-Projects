/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package astarexp;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author Prasheeth Venkat Kumar Assignment: A star Algorithm
 */
public class TileBoard {

    // Array Of Node that represents the Board.
    private final Node[][] board;
    // ArrayList that stores the eligible Neighbor Nodes.
    private final ArrayList<Node> neighborNodes = new ArrayList<>();
    // Variables to store imporant node information
    private Node goalNode;
    private Node startNode;
    private Node currentNode;
    // Min Heap
    PriorityQueue<Node> openList = new PriorityQueue<>();
    //Closed List
    HashMap<String, Node> closedList = new HashMap<>();

    /**
     * Constructor of the TileBoard class that creates a 15x15 node array.
     */
    public TileBoard() {
        // Create a 15x15 board node array and populate it with nodes.
        board = new Node[15][15];
        this.populateBoard();
    }

    /**
     * The method populates the board array with nodes that are both
     * transversable and non-transversable.
     */
    public void populateBoard() {

        // Populate the board with Nodes
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                // Generate random number between 0 and 9.
                int rndNum = (int) (Math.random() * 9);
                // Allotts the random 10% (approx.) of the nodes being created to have the type of non-tranversable and others nodes to be transverable.
                if (rndNum == 1) {
                    board[row][col] = new Node(row, col, 1);
                } else {
                    board[row][col] = new Node(row, col, 0);
                }
            }
        }
    }

    /**
     * The method takes in coordinates that the user wants to find the path
     * between.
     *
     * @param startX the starting X coordinate
     * @param startY the starting y coordinate
     * @param goalX the goal X coordinate
     * @param goalY the goal Y coordinate
     * @throws Exception Limits the parameters from begin invalid in terms the
     * coordinates being out of bounds or transverable.
     */
    public void findPath(int startX, int startY, int goalX, int goalY) throws Exception {
        // Check if the coordinates entered are in bounds.
        if (startX >= 0 && startX <= 14 && goalX >= 0 && goalX <= 14 && startY >= 0 && startY <= 14 && goalY >= 0 && goalY <= 14) {
            // Check if the coordinates enter are transverable.
            if (isTransversable(board[startX][startY].getType()) && isTransversable(board[goalX][goalY].getType())) {
                startNode = board[startX][startY];
                goalNode = board[goalX][goalY];
            } else {
                throw new IOException("Either both or one of the start/goal coordinates entered was invalid. Please enter coordinates that are transverable (looks like O");
            }
        } else {
            throw new IOException("Either both or one of the start/goal coordinates entered was invalid. Please enter coordinates that are between 0, 14 (Inclusive)");
        }
        // Print the tile board to provide visual representation for the user.
        this.printTileBoard();
        // Apply the A start algorithm to find a path between the coordinates.
        this.AStar();

    }

    /**
     * Add the parameters node's all eligible horizontal neighbor nodes to the
     * neighborNodes Array List.
     *
     * @param node The node who horizontal neighbors nodes are generated.
     */
    public void addHorizontal(Node node) {

        // Store node location
        int row = node.getRow();
        int col = node.getCol();

        // Add the right node
        // Edge case check
        if (row < 14) {
            // Check if node is traverseable and is not present in the closed list.
            if (isTransversable(board[row + 1][col].getType()) && !isNodePresent(board[row + 1][col])) {
                // Since the node is an eligiable node, add it to the neighborNodes array list.
                neighborNodes.add(board[row + 1][col]);
            }

        }
        // Add the left node
        // Edge case check
        if (row > 0) {
            // Check if node is traverseable and is not present in the closed list.
            if (isTransversable(board[row - 1][col].getType()) && !isNodePresent(board[row - 1][col])) {
                // Since the node is an eligiable node, add it to the neighborNodes array list.
                neighborNodes.add(board[row - 1][col]);
            }

        }
    }

    /**
     * Add the parameters node's all eligible vertical neighbor nodes to the
     * neighborNodes Array List.
     *
     * @param node The node who vertical neighbors nodes are generated.
     */
    public void addVertical(Node node) {

        // Store node location
        int row = node.getRow();
        int col = node.getCol();

        // Add the lower node 
        // Edge case check
        if (col < 14) {
            // Check if node is traverseable and is not present in the closed list.
            if (isTransversable(board[row][col + 1].getType()) && !isNodePresent(board[row][col + 1])) {
                // Since the node is an eligiable node, add it to the neighborNodes array list.

                neighborNodes.add(board[row][col + 1]);
            }
        }

        // Add the upper node 
        // Edge case check
        if (col > 0) {
            // Check if node is traverseable and is not present in the closed list.
            if (isTransversable(board[row][col - 1].getType()) && !isNodePresent(board[row][col - 1])) {
                // Since the node is an eligiable node, add it to the neighborNodes array list.
                neighborNodes.add(board[row][col - 1]);
            }

        }

    }

    /**
     * The methods call the addVertical() and addHorizontal() method to add all
     * eligible neighbors to the neighborNodes Array List. Calls the
     * populateNodeInfo() method to fill up the neighbor nodes information.
     *
     * @param node The node who neighbors nodes are generated.
     */
    public void addAllNeighbors(Node node) {
        // Clears the previous neighbor nodes.
        neighborNodes.clear();
        // Add the eligible vertical and horizontal nodes and their infomormation to neighborNode Array.
        this.addVertical(node);
        this.addHorizontal(node);
        this.populateNodeInfo();
    }

    /**
     * The method fills up information such as the parent, G Value, F Value, H
     * Value, of the nodes present in the neighborNodes Array List. In addition,
     * the nodes in the neighborNodes are also added to the closed list.
     */
    public void populateNodeInfo() {
        // Loops through the neighborNodes Array and assigns the appropriate information of the nodes present.
        for (Node node : neighborNodes) {
            // Sets parent of the Node.
            node.setParent(currentNode);
            // Sets H value of the Node.
            node.setH(calculateHValue(node));
            // Sets G value of the Node.
            node.setG(calculateGValue(node));
            // Sets F value of the Node.
            node.setF();
            // Adds the node to the open list.
            openList.add(node);
        }
    }

    /**
     * The method determines if a node's type is transversable or
     * non-transversable.
     *
     * @param type int that represents the type of node.
     * @return True if the node's type is 0 (Transversable) and False if the
     * node's type is 1 (non-Transverable).
     */
    public boolean isTransversable(int type) {
        return type == 0;
    }

    /**
     * The method determines if a the parameter Node is present in the
     * closedList.
     *
     * @param node The node that is checked if it is present in the closedList.
     * @return True if the node is found in the closedList and False if the node
     * isn't found in the closedList.
     */
    public boolean isNodePresent(Node node) {
        return closedList.containsKey(node.getKey());
    }

    /**
     * The method that determines the F value of a node.
     *
     * @param node The node who's F value is determined.
     * @return int that represents the F value of the parameter node.
     */
    public int calculateFValue(Node node) {
        return calculateGValue(node) + calculateHValue(node);
    }

    /**
     * The method that determines the H value of a node.
     *
     * @param node The node who's H value is determined.
     * @return int that represents the H value of the parameter node.
     */
    public int calculateHValue(Node node) {
        // Variable to store the h value.
        int hValue;

        // Store node location
        int row = node.getRow();
        int col = node.getCol();

        //Store goal node location
        int gRow = goalNode.getRow();
        int gCol = goalNode.getCol();

        // Calculates the H value by estimating the distance between the node parameter and the goal node.
        hValue = Math.abs((gRow - row)) + Math.abs((gCol - col));
        return hValue;

    }

    /**
     * The method that determines the G value of a node.
     *
     * @param node The node who's G value is determined.
     * @return int that represents the G value of the parameter node.
     */
    public int calculateGValue(Node node) {
        // Check if the node is the starting node.
        if (node.getParent() == null) {
            // Return zero since the parameter node is the starting node.
            return 0;
        } else {
            // Add 10 to the parent node's G value to represent the distance from the starting node and parameter node.
            return node.getG() + 10;
        }

    }

    /**
     * The method that generates and populates the info of the parameter node's
     * neighborNodoes.
     *
     * @param node The node of which the neighbor nodes are generated.
     */
    public void findBestNeighbor(Node node) {
        addAllNeighbors(node);
    }

    /**
     * The method implements the A start algorithm to try and find the shortest
     * path between the startNode and the goalNode.
     */
    public void AStar() {
        // Add the starting node to the openList.
        openList.add(startNode);

        // Loop till nodes are present in the open list.
        while (openList.size() != 0) {
            // Set the currentNode with the node with best F value in the openList.
            currentNode = openList.poll();
            // Populate the openList with the eligible neighbors of the current node.
            findBestNeighbor(currentNode);

            System.out.println(currentNode);
            // Check if the current node is the goal node.
            if (currentNode.equals(goalNode)) {

                System.out.println("Path Found");
                break;
            }

            // Add the check node to the closed list so it isn't generated as a neighbor again.
            closedList.put(currentNode.getKey(), currentNode);
        }

    }

    /**
     * The method that prints the tile board with the shortest path determined
     * by the A start algorithm.
     *
     * @param pathArr ArrayList of nodes that is the shorter path between the
     * start and goal node determined by the A start algorithm.
     */
    public void printTileBoard(ArrayList<Node> pathArr) {
        // Loop through the board array.
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {

                Node curNode = board[row][col];
                // Apply special symbol for the starting node.
                if (curNode == startNode) {
                    System.out.print("S|");
                    // Apply special symbol for the goal node.
                } else if (curNode == goalNode) {
                    System.out.print("G|");
                    // Apply special symbol for the path nodes.
                } else if (pathArr.contains(curNode)) {
                    System.out.print("*|");

                } else {
                    if (curNode.getType() == 1) {
                        System.out.print("/|");
                    } else {
                        System.out.print("O|");
                    }
                }

            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    /**
     * The method that prints the tile board.
     */
    public void printTileBoard() {
        // Loop through the board array.

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Node curNode = board[row][col];
                // Apply special symbol for the starting node.
                if (curNode == startNode) {
                    System.out.print("S|");
                    // Apply special symbol for the goal node.
                } else if (curNode == goalNode) {
                    System.out.print("G|");
                } else {
                    if (curNode.getType() == 1) {
                        System.out.print("/|");
                    } else {
                        System.out.print("O|");
                    }
                }

            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    /**
     * The method that prints the path between the startNode and goalNode
     * determined by the A star algorithm.
     */
    public void printPath() {
        
        if(this.getPath()){
            
        }
        this.getPath().forEach(node -> {
            if (node == goalNode) {
                System.out.print("(X: " + node.getRow() + " Y: " + node.getCol() + ")---->");
            }
        });
    }

    /**
     * The method that creates a ArrayList to keep track of the shortest path
     * between the starNode and goalNode determined by the A start algorithm.
     *
     * @return ArrayList of Nodes that represents the shortest path between the
     * starNode and goalNode determined by the A start algorithm.
     */
    public ArrayList<Node> getPath() {
        ArrayList<Node> path = new ArrayList<>();
        while (currentNode.getParent() != null) {
            path.add(currentNode.getParent());
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);
        return path;

    }

}
