package PriorityQueue;

import Help.Entry;

/**
 * Interface for an adaptable priority queue
 *
 * @param <K> Key
 * @param <V> Value
 * 
 * @author Mattias Majetic
 */
public interface PriorityQueue<K, V>{
	public int size();
	public boolean isEmpty();
	public Entry<K, V> min() throws Exception;
	public Entry<K, V> insert(K k, V v) throws Exception;
	public Entry<K, V> removeMin() throws Exception;
	public Entry<K, V> remove(Entry<K, V> entry) throws Exception;
	public K replaceKey(Entry<K, V> entry, K k) throws Exception;
	public void clear();
}
