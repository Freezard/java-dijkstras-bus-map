package Help;

/**
 * Standard entry class.
 * 
 * @param <K> Key
 * @param <V> Value
 */
public class Entry<K, V>{
	K k;
	V v;
	
	public Entry(K k, V v){
		this.k = k;
		this.v = v;
	}
	
	public void setKey(K k){
		this.k = k;
	}
	
	public K getKey(){ return k;}
	public V getValue(){ return v;}
}