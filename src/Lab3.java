import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import Dijkstras.ShortestPath;
import Graph.Graph;
import Graph.Vertex;
import Lab3Help.*;
import PriorityQueue.AdaptableBinaryHeap;

/**
 * Lets the user select their source and destination
 * of a bus map through a GUI and then displays the map
 * consisting of roads and stops as well as highlighting
 * the shortest path to the destination. 
 * 
 * Uses specific text files to get information about the
 * bus map.
 * 
 * @author Mattias
 */
public class Lab3 implements ActionListener{
	private BusMapFrame bmf;
    private Lab3Frame frame;
    private Lab3File file;
    private Graph g;
    private ShortestPath sp;
    
    private Vector<BStop> stops;
	private Vector<BLineTable> lines;
    private Iterator<String> iter;

    /**
     * Default constructor inserts all the stops and
     * lines in two vectors by reading the respective
     * text files. It also adds all the stops to the
     * GUI for users to select, adds the ActionListener,
     * shows the GUI, sets the priority queue in the
     * ShortestPath as an AdaptableBinaryHeap and lastly
     * creates the graph.
     */
    public Lab3(){
    	bmf = new BusMapFrame();
    	file = new Lab3File();
    	frame = new Lab3Frame();
    	stops = file.readStops("stops-gbg.txt");
    	lines = file.readLines("lines-gbg.txt");
		for(BStop b : stops)
			frame.addStop(b.getName());
    	addGraph();
    	sp = new ShortestPath(g, new AdaptableBinaryHeap());
    	frame.addActionListener(this);
       	frame.setSize(600,300);
    	frame.setVisible(true);
    }
    
    /**
     * The ActionListener of the button of the GUI.
     * Whenever the button is clicked, the shortest path
     * will be calculated based on the selected stops,
     * and the path will be given to the iterator.
     * It then draws the map including the shortest
     * path and prints out the length of the path.
     * 
     * Finally it shows the map.
     * 
     */
    public void actionPerformed(ActionEvent e) {
		if (e.getSource() == frame.spath)
		try {
			sp.computePath((String) frame.getFrom(), (String) frame.getTo());
			iter = sp.getPath();
			drawMap();
			System.out.println(sp.getPathLength());
			bmf.finalMap(); // Only needed once, but will do every time
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    /**
     * Draws the map in a frame using BusFrameMap.
     */
    private void drawMap(){	    
		bmf.initMap(); // In order to clear the shortest path from the drawing
	    HashMap<String, int[]> co = new HashMap<String, int[]>(); // For finding the coordinates
		for (BStop b : stops){									  // of each bus stop
			co.put(b.getName(), new int[]{ b.getX(), b.getY()});
			bmf.drawStop(b.getX(), b.getY(), b.getName());} // Draws all stops
		
		for (BLineTable c : lines){
			bmf.nextColor();
			for (int i = 0;i < (c.getStops().length + 1) / 2;i++){ // Draws all lines in different
				BLineStop a = c.getStops()[i],					   // colors. Does not include
						  b = c.getStops()[i+1];				   // duplicates (line from a to b,
				int[] v = co.get(a.getName()),					   // b to a will only be printed once)
					  w = co.get(b.getName());
				bmf.drawEdge(v[0], v[1], w[0], w[1]);	
			}
		}
    	bmf.initShortestPath();			// Draws the shortest path
    	int[] v = null, w = null;
    	int i = 0;
    	String next;
		while (iter.hasNext()){
			if (i % 2 == 0){
				next = iter.next();
				v = co.get(next);
			}
			else{
				next = iter.next();
				w = co.get(next);
			}
			if (w != null)	// Only first time
				bmf.drawEdge(v[0], v[1], w[0], w[1]);
			i++;
			System.out.println(next); // Prints out the names of the stops of the path
		}
    }

    /**
     * Builds the graph using the vectors containing the stops and lines.
     */
    private void addGraph(){
    	g = new Graph();
		HashMap<String, Vertex> m = new HashMap<String, Vertex>();
		Vertex v, w;
		for (BLineTable b : lines){
			BLineStop[] k = b.getStops();
			for (int j = 1;j < k.length;j++){  // Add a set of stops to the graph
				if (!m.containsKey(k[j-1].getName())){ // Stop not already been added?
					v = g.insertVertex(k[j-1].getName()); // Add it and mark it as added
					m.put(k[j-1].getName(), v);
				}
				if (!m.containsKey(k[j].getName())){				
					w = g.insertVertex(k[j].getName());
					m.put(k[j].getName(), w);
				}						// Insert an edge from the source to the destination
				g.insertEdge(m.get(k[j-1].getName()), m.get(k[j].getName()), k[j].getTime());
			}
		}
    }
	
	public static void main(String[] args) throws Exception{
		Lab3 p = new Lab3();
	}
}
