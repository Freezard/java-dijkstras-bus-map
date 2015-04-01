package Dijkstras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Graph.*;
import Help.Entry;
import PriorityQueue.PriorityQueue;

/**
 * Computes the shortest path using Dijkstra's algorithm,
 * a directed graph using adjacency lists and a priority queue.
 * Gives a string representation of the nodes of the shortest
 * path as well as the length of it.
 * 
 * @author Mattias Majetic
 */
public class ShortestPath implements Path{
	private Graph g;
	private PriorityQueue<Integer, Vertex> pq;
	private HashMap<String, Integer> shortest;
	private HashMap<String, Entry<Integer, Vertex>> visited;
	private HashMap<Entry<Integer, Vertex>, Entry<Integer, Vertex>> path;
	protected Iterator<String> i;
	protected int length;
	
	/**
	 * Takes a graph and a priority queue for computing
	 * the shortest path.
	 * 
	 * @param graph Directed graph using adjacency lists
	 * @param pq Adaptable priority queue
	 */
	public ShortestPath(Graph graph, PriorityQueue<Integer, Vertex> pq){
		g = graph;
		this.pq = pq;
		shortest = new HashMap<String, Integer>();
		visited = new HashMap<String, Entry<Integer, Vertex>>();
		path = new HashMap<Entry<Integer, Vertex>, Entry<Integer, Vertex>>();
		}
	
	/**
	 * Computes the shortest path from a node
	 * to another with the help of a graph and
	 * a priority queue.
	 * 
	 * @param from Source node
	 * @param to Destination node
	 * @throws Exception If something wrong happened
	 */
    public void computePath(String from, String to) throws Exception{
		Entry<Integer, Vertex> v = null;
		if (shortest.get(from) != null && shortest.get(from) == 0)
			if (shortest.containsKey(to)){ // If the source is the same as previous time,
				v = visited.get(to);	   // and the shortest path to the destination
				buildPath(v, from);		   // has already been found,
				return;					   // build the path directly using this info
			}
		pq.insert(0, g.find(from));	// Insert the source vertex into the priority queue
    	shortest.clear();
    	visited.clear();
    	path.clear();
    	
		while (!pq.isEmpty()){
			Entry<Integer, Vertex> w;	// Remove the element with the highest priority
			v = pq.removeMin();			// and assign it to v. The shortest path to this
			shortest.put(v.getValue().getName(), v.getKey()); // vertex has been found, add it
			if (v.getValue().getName().equals(from))	// ONLY the first time. Mark the source
				visited.put(v.getValue().getName(), v); // vertex as visited
			if (v.getValue().getName().equals(to)){		// Is this vertex the destination?
				pq.clear();								// Then no need to continue searching,
				break;									// clear the queue and start building
			}											// the path
			for (Edge e : g.incidentEdges(v.getValue())){		  // For every adjacent vertex: 
					if (visited.containsKey(e.getTo().getName())) // Been visited before?
						w = visited.get(e.getTo().getName());	  // Then just get it
					else{
						w = pq.insert(Integer.MAX_VALUE, e.getTo()); // Else insert it and mark
						visited.put(w.getValue().getName(), w);		 // it as visited
					}
					int x = Math.min(w.getKey(), v.getKey() + e.getWeight());
					if (x < w.getKey()){	 // If the length to v plus the length from
						pq.replaceKey(w, x); // v to w is shorter than the length to w.
						path.put(w, v);		 // Decrease the length to w and mark v as the
				}							 // previous vertex in the shortest path to w
			}
		}
		if (!v.getValue().getName().equals(to))		 // So was the destination found?
			throw new Exception("No path to " + to); // If not, there is no path to it
		else
		buildPath(v, from);							 // Else build the path
    }
    
    /**
     * Builds an iterator of the shortest path consisting of names
     * of the nodes. Also sets the total length of the path.
     * 
     * @param entry Destination node
     * @param from Source node
     */
    private void buildPath(Entry<Integer, Vertex> entry, String from){
    	ArrayList<String> a = new ArrayList<String>();
    	length = entry.getKey(); // Set the length to the destination
		for (Entry<Integer, Vertex> e = entry;;e = path.get(e)){ // Backtracks from the destination
			a.add(e.getValue().getName());					     // to the source and adds the
			if (e.getValue().getName().equals(from))			 // name of each vertex
				break;
		}
		i = a.iterator();
    }
    
    /**
     * Returns an iterator with names of the nodes of the path.
     * @return Iterator with the names of the nodes of the path
     */
    public Iterator<String> getPath(){
    	return i;
    }
    
    /**
     * Returns the length of the path.
     * 
     * @return Length of the path
     */
    public int getPathLength(){
    	return length;
    }
}
