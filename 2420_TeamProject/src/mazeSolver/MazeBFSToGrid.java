package mazeSolver;

import java.awt.Color;
import java.util.Iterator;
import javax.swing.JLabel;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;

/**
 * Provides breadth-first-search functionality that can be used to find the
 * shortest path from a start position to an end position on the grid of cells
 * created by <code>MazeSolverGUI</code>.
 * 
 * @author Mohamed Mohamed
 * @author Anthony Barnes
 *
 */
public class MazeBFSToGrid {
    private JLabel[] gridPanel;
    private Graph graph;
    private static final Color GREEN_COLOR = new Color(0, 153, 0);
    private static final Color RED_COLOR = new Color(204, 0, 0);

    /**
     * Constructor of class MazeBFSToGrid. Initializes the gridPanel and
     * graph fields.
     * 
     * @param gridPanel collection of JLabels that make up the GUI's grid.
     * @param graph graph representation of the cells on the GUI's grid.
     */
    public MazeBFSToGrid(JLabel[] gridPanel, Graph graph) {
        this.gridPanel = gridPanel;
        this.graph = graph;
    }

    /**
     * Finds the shortest path on the GUI's grid from the start position to
     * the end position.
     * 
     * @return an <code>Iterable</code> containing the indexes of the cells
     * that make up the shortest path from start to end on the GUI's grid.
     */
    public Iterable<Integer> shortestPath() {
        int source = 0;
        int end = 0;
        for (int i = 0; i < gridPanel.length; i++) {
            if (gridPanel[i].getBackground().equals(GREEN_COLOR)) {
                source = i;
            }
            if (gridPanel[i].getBackground().equals(RED_COLOR)) {
                end = i;
            }
        }

        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, source);

        return bfs.pathTo(end);
    }


	@Override
	public String toString() {
		Iterable<Integer> pathsBFS = shortestPath();
		if (pathsBFS == null) {
			return "Path from start position to end position does not exist.";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Path steps from start to finish:\n");
		
		Iterator<Integer> BFSIterator = pathsBFS.iterator();
		int last = BFSIterator.next() + 1;
		int current;
		while (BFSIterator.hasNext()) {
			current = BFSIterator.next() + 1;
			sb.append(last + " => " + current + "\n");
			last = current;
		}

		return sb.toString();
	}
}
