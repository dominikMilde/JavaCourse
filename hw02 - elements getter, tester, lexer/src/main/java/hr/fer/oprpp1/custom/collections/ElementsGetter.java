package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * @author Dominik
 * Serves as some kind of iterator
 * Sets methods that implementations need to have
 */
public interface ElementsGetter{
	 /**
	 * @return true if collection has next element, false otherwise
	 * @throws ConcurrentModificationException if collection we iterate on is manipulated on some way
	 */
	boolean hasNextElement();
	 /**
	 * @return element from collection that is next
	 * @throws ConcurrentModificationException if collection we iterate on is manipulated on some way
	 */
	Object getNextElement();
	 /**
	  * processes remaining objects in collection
	 * @param p reference to Processor we set to do something with remaining elements
	 * @throws ConcurrentModificationException if collection we iterate on is manipulated on some way
	 * @throws NoSuchElementException when we get to the end of collection
	 */
	default void processRemaining(Processor p) {
		 while(hasNextElement()) {
			 p.process(getNextElement());
		 }
	 }
}