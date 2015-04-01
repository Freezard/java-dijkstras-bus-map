package Graph;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * A directed graph using adjacency lists. Consists of
 * vertices and edges.  
 * 
 * @author Mattias Majetic
 */
public class Graph{
	private TreeMap<Vertex, ArrayList<Edge>> vertices;
	private ArrayList<Edge> edges;
	
	/**
	 * Default constructor only initializes the instant
	 * variables.
	 */
	public Graph(){
		vertices = new TreeMap<Vertex, ArrayList<Edge>>();
		edges = new ArrayList<Edge>();
	}
	
	/**
	 * Inserts a new vertex with the specified name
	 * and returns it.
	 * 
	 * @param name Name of the vertex
	 * @return Inserted vertex
	 */
	public Vertex insertVertex(String name){
		Vertex v = new Vertex(name);
		vertices.put(v, new ArrayList<Edge>());
		return v;
	}
	
	/**
	 * Given two vertices, inserts a new edge between
	 * them. It is directed.
	 * 
	 * @param v Source
	 * @param w Destination
	 * @param i Weight of the edge
	 */
	public void insertEdge(Vertex v, Vertex w, Integer i){
		Edge e = new Edge(v, w, i);
		vertices.get(v).add(e);
		edges.add(e);
	}
	
	/**
	 * Finds and returns the vertex with the
	 * specified name.
	 * 
	 * @param name Name of the vertex
	 * @return Found vertex, null if not found
	 */
	public Vertex find(String name){
		for (Vertex x : vertices.keySet())
			if (x.getName().equals(name))
				return x;
		return null;
	}
	
	/**
	 * Returns the incident edges of the specified
	 * vertex.
	 * 
	 * @param v Vertex
	 * @return A list of the edges
	 */
	public ArrayList<Edge> incidentEdges(Vertex v){
		return vertices.get(v);
	}

	/**
	 * Given a vertex and an edge, returns
	 * the opposite vertex if the vertex exists
	 * in the edge.
	 * 
	 * @param v Vertex
	 * @param e Edge
	 * @return Opposite vertex or null if v not in e
	 */
	public Vertex opposite(Vertex v, Edge e){
		if (e.getFrom() == v)
			return e.getTo();
		else if (e.getTo() == v)
			return e.getFrom();
		else return null;
	}
	
	/**
	 * Returns the source of the specified
	 * edge.
	 * 
	 * @param e Edge
	 * @return Source of the edge
	 */
	public Vertex origin(Edge e){
		return e.getFrom();
	}
	
	/**
	 * Returns the destination of the specified
	 * edge.
	 * 
	 * @param e Edge
	 * @return Destination of the edge
	 */
	public Vertex destination(Edge e){
		return e.getTo();
	}
	
	/**
	 * Given two vertices, checks if they are
	 * adjacent to each other.
	 * 
	 * @param v First vertex
	 * @param w Second vertex
	 * @return True if they are adjacent
	 * 		   False if not
	 */
	public boolean areAdjacent(Vertex v, Vertex w){
		for (Edge e : incidentEdges(v))
			if (e.getFrom() == w || e.getTo() == v)
				return true;
		return false;
	}
	
	/**
	 * Removes the specified vertex from the graph.
	 * 
	 * @param v Vertex
	 */
	public void removeVertex(Vertex v){
		for (Edge e : incidentEdges(v))
			edges.remove(e);
		vertices.remove(v);
	}
	
	/**
	 * Removes the specified edge from the graph.
	 * 
	 * @param e Edge
	 */
	public void removeEdge(Edge e){
		vertices.get(origin(e)).remove(e);
		vertices.get(destination(e)).remove(e);
		edges.remove(e);
	}
	
	/**
	 * Returns a set of all vertices in the graph.
	 * 
	 * @return A set of all vertices
	 */
	public Set<Vertex> vertices(){
		return vertices.keySet();
	}
	
	/**
	 * Returns a list of all edges in the graph.
	 * 
	 * @return A list of all edges
	 */
	public ArrayList<Edge> edges(){
		return edges;
	}
}
