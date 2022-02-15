package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 *represents some general collection of objects
 */
public class Collection {
	/**
	 * Constructors will be implemented in more specific classes
	 */
	protected Collection() {
		
	}
	
	/**
	 * 
	 * @return true if collection contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		if(size()==0) return true;
		return false;	
	}
	/**
	 * @return Returns the number of currently stored objects in this collections.
	 */
	public int size() {
		return 0;
	}
	/**
	 * Adds the given object into this collection. Implement it here to do nothing.
	 * @param value reference to Object that is added to collection
	 */
	public void add(Object value) {
		
	}
	/**
	 * Returns true only if the collection contains given value, as determined by equals method.
	 * @param value Object we search for
	 * @return boolean
	 */
	public boolean contains(Object value) {
		return false;
	} 
	/**
	 * Returns true only if the collection contains given value as determined by equals method and removes
one occurrence of it
	 * @param value Object we want to remove
	 * @return
	 */
	public boolean remove(Object value) {
		return false;
	}
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and
returns the array. This method never returns null.
	 * @return new array filled with values from current collection
	 * @throws UnsupportedOperationException if not implemented
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
will be sent is undefined in this class.
	 * @param processor class from which we call process()
	 */
	public void forEach(Processor processor) {	
	}
	/**
	 * Method adds into the current collection all elements from the given collection. This other collection
remains unchanged.
	 * @param other Collection we add FROM
	 */
	public void addAll(Collection other) {
		class ProcessorAddsToCollection extends Processor{
			public void process(Object value) {
				add(value);
				
			}
		}
		other.forEach(new ProcessorAddsToCollection());
	}
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		
	}
}
