package PriorityQueue;

import java.util.Comparator;
import java.util.HashMap;
import Help.*;

/**
 * Priority Queue implemented by an adaptable binary heap.
 * Uses a dictionary to remember the positions of the elements.
 *
 * @param <K> Key
 * @param <V> Value
 * 
 * @author Mattias Majetic
 */
public class AdaptableBinaryHeap<K, V> implements PriorityQueue<K, V>{
	private ArrayListCompleteBinaryTree<Entry<K, V>> heap;
	private HashMap<Entry<K, V>, Position<Entry<K, V>>> pos;
	private Comparator<K> comp;
	
	/**
	 * Default constructor initializes the instant variables and
	 * sets the comparator to use natural ordering.
	 */
	public AdaptableBinaryHeap(){
		heap = new ArrayListCompleteBinaryTree<Entry<K, V>>();
		pos = new HashMap<Entry<K, V>, Position<Entry<K, V>>>();
		comp = new DefaultComparator<K>();
	}
	
	/**
	 * For use if custom comparator.
	 * 
	 * @param c Custom comparator
	 */
	public AdaptableBinaryHeap(Comparator<K> c){
		heap = new ArrayListCompleteBinaryTree<Entry<K, V>>();
		pos = new HashMap<Entry<K, V>, Position<Entry<K, V>>>();
		comp = c;
	}
	
	/**
	 * Returns the size of the heap.
	 * 
	 * @return Size of the heap
	 */
	public int size(){ return heap.size();}
	
	/**
	 * Returns whether the heap is empty or not.
	 * 
	 * @return True if the heap is empty
	 * 		   False if not
	 */
	public boolean isEmpty(){ return heap.size() == 0;}
	
	/**
	 * Returns the minimum element of the heap.
	 * 
	 * @return Minimum element
	 * @throws Exception If heap is empty
	 */
	public Entry<K, V> min() throws Exception{
		if (isEmpty()) throw new Exception("Empty heap");
		return heap.root().element();
	}
	
	/**
	 * Inserts a new element with the specified key and value
	 * and returns it.
	 * 
	 * @param k Key
	 * @param v value
	 * @return Inserted element
	 * @throws Exception If the key is of wrong type
	 */
	public Entry<K, V> insert(K k, V v) throws Exception{
		checkKey(k);
		Entry<K, V> entry = new Entry<K, V>(k, v); // m is the same as entry with
		Position<Entry<K, V>> m = heap.add(entry); // an added position variable.
		pos.put(entry, m); 						   // Assign the entry with its position.
		upHeap(m);								   // The element is added last, thus upHeap
		return entry;
	}
	
	/**
	 * Removes and returns the minimum element of the heap.
	 * 
	 * @return Minimum element
	 * @throws Exception If empty heap
	 */
	public Entry<K, V> removeMin() throws Exception{
		if (isEmpty()) throw new Exception("Empty heap");
		Entry<K, V> min = heap.root().element();
		if (size() == 1)				// Just one element in the heap,
			pos.remove(heap.remove());  // remove it (the root).
		else{
			pos.put(heap.T.get(size()).element(), heap.root()); // Else replace the root element
			pos.remove(min);									// with the last element and remove
			heap.replace(heap.root(), heap.remove());			// the last node.
			downHeap(heap.root());								// downHeap the new root element to its
		}														// correct position
		return min;
	}
	
	/**
	 * Removes and returns a specified element in the heap.
	 * 
	 * @param entry Element to be removed
	 * @return Removed element
	 * @throws Exception If empty heap, null or wrong element
	 */
	public Entry<K, V> remove(Entry<K, V> entry) throws Exception{
		if (size() == 1)
			pos.remove(heap.remove());
		else{
			Position<Entry<K, V>> p = pos.get(entry); // Retrieve the location of the element.
			pos.remove(entry);
			pos.put(heap.T.get(size()).element(), p); // Replace the element at the specified
			heap.replace(p, heap.remove());			  // node with the last element, remove the
			downHeap(p);							  // last node. The previous element at the
		}											  // specified node must be lower than its
		return entry;								  // current value, thus just downHeap it
	}
	
	/**
	 * Replaces the key of the specified element
	 * with a new one.
	 * 
	 * @param entry Element
	 * @param k New key
	 * @return New key
	 * @throws Exception Various
	 */
	public K replaceKey(Entry<K, V> entry, K k) throws Exception{
		Position<Entry<K, V>> p = pos.get(entry);
		p.element().setKey(k);
		upHeap(p);					// New key can be larger or smaller than previous,
		downHeap(p);				// thus both upHeap and downHeap it
		return k;
	}
	
	/**
	 * Checks if two keys are of the same type.
	 * 
	 * @param key Key to compare with
	 * @throws Exception If not of the same type
	 */
	protected void checkKey(K key) throws Exception{
		try{
			comp.compare(key, key);
		}
		catch(Exception e){
			throw new Exception("Not of the same type");
		}
	}
	/**
	 * Bubble up a specified element by checking if it
	 * is smaller than its parent, if so then swap places.
	 * Repeat until eventually at the root.
	 * 
	 * @param v Element to bubble up
	 * @throws Exception Various
	 */
	protected void upHeap(Position<Entry<K, V>> v) throws Exception{
		Position<Entry<K, V>> u;
		while (!heap.isRoot(v)){
			u = heap.parent(v);
			if (comp.compare(u.element().getKey(), v.element().getKey()) <= 0)
				break;
			swap(u, v);
			v = u; // v becomes its parent... re-check it
		}
	}
		
	/**
	 * Bubble down a specified element while retaining
	 * the heap property.
	 * 
	 * @param r Element to bubble down
	 * @throws Exception Various
	 */
	protected void downHeap(Position<Entry<K, V>> r) throws Exception{
		while (heap.isInternal(r)){ // Must have a left child
			Position<Entry<K, V>> s;
			if (!heap.hasRight(r))
				s = heap.left(r);	// No right child
			else if (comp.compare(heap.left(r).element().getKey(), heap.right(r).element().getKey()) <= 0)
				s = heap.left(r);	// Left child is smaller than right child 
			else
				s = heap.right(r);	// Right child is smaller
			if (comp.compare(s.element().getKey(), r.element().getKey()) < 0){
				swap(r, s); // Swap the elements if child is smaller
				r = s;
			}
			else break;
		}
	}
	
	/**
	 * Swaps the elements of two nodes.
	 * 
	 * @param x First node
	 * @param y Second node
	 * @throws Exception Various
	 */
	protected void swap(Position<Entry<K, V>> x, Position<Entry<K, V>> y) throws Exception{
		pos.put(x.element(), y);
		pos.put(y.element(), x);
		Entry<K, V> temp = x.element();
		heap.replace(x, y.element());
		heap.replace(y, temp);
	}
	
	/**
	 * Clears the heap.
	 */
	public void clear(){
		heap.clear();
		pos.clear();
	}
	
	/**
	 * String representation.
	 */
	public String toString(){
		return heap.toString();
	}
}