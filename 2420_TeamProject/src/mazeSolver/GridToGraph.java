package mazeSolver;

import edu.princeton.cs.algs4.Graph;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Utility that can be used to convert an array of cells from the grid created
 * by <code>MazeSolverGUI</code> to a graph that can be processed to reveal
 * information about the orientation of those cells.
 * 
 * @author Mohamed Mohamed
 * @author Anthony Barnes
 *
 */
public class GridToGraph {
    private JLabel[] gridLabels;
    
    /**
     * Constructor of class GridToGraph. Initializes the gridLabels field.
     * 
     * @param gridLabels
     */
    public GridToGraph(JLabel[] gridLabels) {
        this.gridLabels = gridLabels;
    }
    
    private int[][] arrayConvert(int rows, int cols) {
        int[][] grid2D = new int[rows][cols];
        for (int i = 0; i < gridLabels.length; i++) {
            int row = i / cols;
            int col = i % cols;
            grid2D[row][col] = 
            		(gridLabels[i].getBackground() != Color.BLACK) ? 1 : 0;
        }
        return grid2D;
    }
    
    /**
     * Converts the JLabels representing the cells in the GUI's grid into
     * a <code>Graph</code> that can be processed to obtain information
     * about the orientation of the cells.
     * 
     * @param numRows the number of rows in the GUI's grid.
     * @param numCols the number of columns in the GUI's grid.
     * @return a graph representing the cells in the GUI's grid.
     */
    public Graph convert(int numRows, int numCols) {
        int[][] array = arrayConvert(numRows, numCols);
        Graph graph = new Graph(gridLabels.length);
        
        // Iterate over the rows and columns of the array
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                // Adds an edge to the right and bottom of non-zero element
                if (array[row][col] != 0) {
                    int v = row * numCols + col;
                    if (col < numCols - 1 && array[row][col + 1] != 0) {
                        int w = row * numCols + (col + 1);
                        graph.addEdge(v, w);
                    }
                    if (row < numRows - 1 && array[row + 1][col] != 0) {
                        int w = (row + 1) * numCols + col;
                        graph.addEdge(v, w);
                    }
                }
            }
        }
        
        return graph;
    }
}
