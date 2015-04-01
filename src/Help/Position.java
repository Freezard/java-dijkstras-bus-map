package Help;

/**
 * Any object with a specific element.
 * 
 * @param <E> Specific element
 */
public interface Position<E> {
	E element() throws Exception;
}
