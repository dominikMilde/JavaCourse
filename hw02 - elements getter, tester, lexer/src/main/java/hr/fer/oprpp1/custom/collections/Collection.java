package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 *represents some general collection of objects and defines methods
 */
public interface Collection {
	 /**
	 * @return new ElementsGeter
	 */
	ElementsGetter createElementsGetter();
	/**
	 * 
	 * @return number of elements in collection
	 */
	int size();
	/**
	 * Adds given object to collection
	 * @param value Object to add
	 */
	void add(Object value);
	/**
	 * Checks whether given object is in collection
	 * @param value object we search for
	 * @return true if contains else otherwise
	 */
	boolean contains(Object value);
	/**
	 * removes Object with value "value" from collection
	 * @param value Object to remove
	 * @return true if successful removal, else otherwise
	 */
	boolean remove(Object value);
	/**
	 * Copies elements from collection to new array and returns that array
	 * @return new Array of objects
	 */
	Object[] toArray();
	
	
	/**
	 * Removes all elements from collection
	 */
	void clear();
	/**
	 * Method adds into the current collection all elements from the given collection. This other collection remains unchanged.
	 * @param other Collection we add FROM
	 */
	default void addAll(Collection other) {
		class ProcessorAddsToCollection implements Processor{
			public void process(Object value) {
				add(value);
				
			}
		}
		other.forEach(new ProcessorAddsToCollection());
	}
	/**
	 * 
	 * @return true if collection contains no objects and false otherwise.
	 */
	default boolean isEmpty() {
		if(size()==0) return true;
		return false;	
	}
	/** Adds all element from collection that satisfy condition that tester requires
	 * @param col  collection to copy from
	 * @param tester tester class from which we take our condition
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter eg = col.createElementsGetter();
		while (eg.hasNextElement()) {
			Object obj = eg.getNextElement();
			if(tester.test(obj)) add(obj);
		}
	}
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
will be sent is undefined in this class.
	 * @param processor class from which we call process()
	 */
	default void forEach(Processor processor) {
		ElementsGetter eg = createElementsGetter();
		while(eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	}
}
