package astarexp;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prasheeth Venkat
 */
public class AStarExp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Variables to store the user input of the start and goal coordinates.
        int startX = 0, startY = 0, goalX = 0, goalY = 0;
        // Create a new board object
        TileBoard board = new TileBoard();
        // print the initial tile board
        board.printTileBoard();
        // Take in user input
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Starting and Ending Coordinates seperated by commas. For Example: Enter '1,2,3,4' if (1,2) and (3,4) are the starting and goal points respectively");
        try {
            // Process user input
            String coor = scan.nextLine();  // Read user input
            String[] coordin = coor.split(",");
            System.out.println("The Start X: " + coordin[0] + " The Start Y: " + coordin[1]);
            System.out.println("The end X: " + coordin[2] + " The end Y: " + coordin[3]);
            // Set the start and goal coordinates to the user's input.
            startX = Integer.parseInt(coordin[0]);
            startY = Integer.parseInt(coordin[1]);
            goalX = Integer.parseInt(coordin[2]);
            goalY = Integer.parseInt(coordin[3]);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            // Try and find the path from the start point to the goal point using the Astart algorithm.
            board.findPath(startX, startY, goalX, goalY);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        // Print board
        board.printTileBoard();
        // Print the updated tile board with parth taken
        board.printTileBoard(board.getPath());
        // Print coordinates path taken.
        board.printPath();
    }

}
