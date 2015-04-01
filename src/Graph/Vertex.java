package Graph;

/**
 * A vertex in a graph that may or may not be connected
 * to other vertices by incident edges. Stores the name
 * of the vertex.
 * 
 * @author Mattias Majetic
 */
public class Vertex implements Comparable<Vertex>{
	private String name;

	/**
	 * Creates a new vertex with the specified name.
	 * 
	 * @param name Name of the vertex/node
	 */
	public Vertex(String name) {
		this.name = name;
	}

	public String getName(){ return name;}

	/**
	 * Compares the two vertices' names.
	 */
	public int compareTo(Vertex e) {
		return name.compareTo(e.name);
	}
}