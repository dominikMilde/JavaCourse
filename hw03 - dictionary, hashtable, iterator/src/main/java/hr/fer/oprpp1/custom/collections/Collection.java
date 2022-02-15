package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 *represents some general collection of objects and defines methods
 */
public interface Collection<T> {
	 /**
	 * @return new ElementsGeter
	 */
	ElementsGetter<T> createElementsGetter();
	/**
	 * 
	 * @return number of elements in collection
	 */
	int size();
	/**
	 * Adds given object to collection
	 * @param value Object to add
	 */
	void add(T value);
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
	default void addAll(Collection<? extends T> other) {
		other.forEach((Processor<? super T>) value -> add(value));
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
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		@SuppressWarnings("unchecked")
		ElementsGetter<T> eg = (ElementsGetter<T>) col.createElementsGetter();
		while (eg.hasNextElement()) {
			T obj = eg.getNextElement();
			if(tester.test(obj)) add(obj);
		}
	}
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
will be sent is undefined in this class.
	 * @param processor class from which we call process()
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> eg = createElementsGetter();
		while(eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	}
}
