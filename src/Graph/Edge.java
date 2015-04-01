package Graph;

/**
 * An edge in a graph that connect two vertices
 * to each other. Has a source, destination and
 * a weight (the length of the edge).
 * 
 * @author Mattias Majetic
 */
public class Edge{
	private Vertex from;
	private Vertex to;
	private int weight;
	
	/**
	 * Creates a new edge with the specified values.
	 * 
	 * @param v Source
	 * @param w Destination
	 * @param weight Length of the edge
	 */
	public Edge(Vertex v, Vertex w, Integer weight){
		from = v;
		to = w;
		this.weight = weight;
	}
		
	public Vertex getFrom(){return from;}
	public Vertex getTo(){ return to;}
	public int getWeight(){ return weight;}
}