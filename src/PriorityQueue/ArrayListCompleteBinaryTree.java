package PriorityQueue;

import java.util.ArrayList;
import java.util.Iterator;
import Help.Position;

/**
 * A standard complete binary tree taken from the
 * lecture book. Uses an array.
 *
 * @param <E> Specific element
 */
public class ArrayListCompleteBinaryTree<E> {
	protected ArrayList<BTPos<E>> T;
	
	protected static class BTPos<E> implements Position<E>{ 
		E element;
		int index;
		
		public BTPos(E elt, int i){
			element = elt;
			index = i;
		}
		
		public E element(){ return element;}
		public int index(){ return index;}
		
		public E setElement(E elt){
			E temp = element;
			element = elt;
			return temp;
		}
	}
	
	public ArrayListCompleteBinaryTree(){
		T = new ArrayList<BTPos<E>>();
		T.add(0, null);
	}
	
	public int size(){ return T.size() - 1;}
	public boolean isEmpty(){ return (size() == 0);}
	public void clear(){
		T.clear();
		T.add(0, null);
	}
	
	public boolean isInternal(Position<E> v) throws Exception{
		return hasLeft(v);
	}
	
	public boolean isExternal(Position<E> v) throws Exception{
		return !isInternal(v);
	}
	
	public boolean isRoot(Position<E> v) throws Exception{
		BTPos<E> vv = checkPosition(v);
		return vv.index() == 1;
	}
	
	public boolean hasLeft(Position<E> v) throws Exception{
		BTPos<E> vv = checkPosition(v);
		return 2*vv.index() <= size();
	}
	
	public boolean hasRight(Position<E> v) throws Exception{
		BTPos<E> vv = checkPosition(v);
		return 2*vv.index() + 1 <= size();
	}
	
	public Position<E> root() throws Exception{
		if (isEmpty()) throw new Exception("Empty tree");
		return T.get(1);
	}
	
	public Position<E> left(Position<E> v) throws Exception{
		if (!hasLeft(v)) throw new Exception("Has no left");
		BTPos<E> vv = checkPosition(v);
		return T.get(2*vv.index());
	}
	
	public Position<E> right(Position<E> v) throws Exception{
		if (!hasRight(v)) throw new Exception("Has no right");
		BTPos<E> vv = checkPosition(v);
		return T.get(2*vv.index() + 1);
	}
	
	public Position<E> parent(Position<E> v) throws Exception{
		if (isRoot(v)) throw new Exception("Has no parent");
		BTPos<E> vv = checkPosition(v);
		return T.get(vv.index()/2);
	}
	
	public E replace(Position<E> v, E o) throws Exception{
		BTPos<E> vv = checkPosition(v);
		return vv.setElement(o);
	}
	
	public Position<E> add(E e){
		int i = size() + 1;
		BTPos<E> p = new BTPos<E>(e, i);
		T.add(i, p);
		return p;
	}
	
	public E remove() throws Exception{
		if (isEmpty()) throw new Exception("Empty tree");
		return T.remove(size()).element();
	}
	
	protected BTPos<E> checkPosition(Position<E> v) throws Exception{
		if (v == null || !(v instanceof BTPos)) throw new Exception ("Null or wrong object");
		return (BTPos<E>) v;
	}
	
	public void swapElements(Position<E> v, Position<E> w) throws Exception{
	    BTPos<E> vv = checkPosition(v);
	    BTPos<E> ww = checkPosition(w);
	    E temp = vv.element();
	    vv.setElement(ww.element());
	    ww.setElement(temp);
	  }
	
	public Iterator<E> iterator(){
		ArrayList<E> list = new ArrayList<E>();
		Iterator<BTPos<E>> iter = T.iterator();
		iter.next();
		while (iter.hasNext())
			list.add(iter.next().element());
		return list.iterator();
	}

}
