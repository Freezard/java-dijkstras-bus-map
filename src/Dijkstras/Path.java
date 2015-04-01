package Dijkstras;

import java.util.Iterator;

/**
 * Interface to compute the shortest path,
 * get an iterator of the path and get the
 * total length of the path.
 * 
 * @author Mattias Majetic
 */
public interface Path{
	/**
	 * Computes the shortest path from a node
	 * to another with the help of a graph and
	 * a priority queue.
	 * 
	 * @param from Source node
	 * @param to Destination node
	 * @throws Exception If something wrong happened
	 */
    public void computePath(String from, String to) throws Exception;
    
    /**
     * Returns an iterator with elements of the path.
     * @return Iterator with the elements of the path.
     */
    public Iterator getPath();
    
    /**
     * Returns the length of the path.
     * 
     * @return Length of the path.
     */
    public int getPathLength();
}
