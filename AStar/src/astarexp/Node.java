package astarexp;

/**
 *
 * @author Prasheeth Venkat
 */
public class Node implements Comparable<Node> {

    private int row, col, f, g, h, type;
    private Node parent;

    /**
     * The constructor of the Node class.
     * @param r int that represents the row of the node
     * @param c int that represents the column of the node
     * @param t int that represents the type of the node (Transversable or non-transversable)
     */
    public Node(int r, int c, int t) {
        row = r;
        col = c;
        type = t;
        parent = null;
        //type 0 is traverseable, 1 is not
    }

    //mutator methods to set values

    /**
     * Setter method for F which adds g and h together.
     */
    public void setF() {
        f = g + h;
    }

    /**
     * Setter method for G.
     * @param value int that represents the g value that would be set.
     */
    public void setG(int value) {
        g = value;
    }

    /**
     * Setter method for H.
     * @param value int that represents the H value that would be set.
     */
    public void setH(int value) {
        h = value;
    }

    /**
     * Setter method for Parent.
     * @param n node that represents the parent that will be set.
     */
    public void setParent(Node n) {
        parent = n;
    }

    //accessor methods to get values

    /**
     * Getter method for F.
     * @return int that represents the F value.
     */
    public int getF() {
        return f;
    }

    /**
     * Getter method for G.
     * @return int that represents the G value.
     */
    public int getG() {
        return g;
    }

    /**
     * Getter method for
     * @return int that represents the H value.
     */
    public int getH() {
        return h;
    }

    /**
     * Getter method to get a node's parent node.
     * @return node that represents the node's parent node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Getter method for the row of node.
     * @return int that represents the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter method for the column of the node.
     * @return int that represents the column.
     */
    public int getCol() {
        return col;
    }

    /**
     * Getter method for the node's type.
     * @return int that is of the value of 1 if non-transversable and 0 if transversable.
     */
    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object in) {
        //typecast to Node
        Node n = (Node) in;

        return row == n.getRow() && col == n.getCol();
    }

    @Override
    public String toString() {
        return "Node: " + row + "_" + col;
    }

    /**
     * A method that gets a unique key value.
     * @return String that represents the key of the node.
     */
    public String getKey() {
        String key = Integer.toString(row) + Integer.toString(col);
        return key;
    }

    @Override
    public int compareTo(Node o) {
        // Compares the F value.
        if (this.getF() > o.getF()) {
            return 1;
        } else if (this.getF() < o.getF()) {
            return -1;
        } else {
            return 0;
        }
    }

}
