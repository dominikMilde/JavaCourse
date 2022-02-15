package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 * Does some testing over given object
 */
public interface Tester<T> {
	/**
	 * @param obj to test
	 * @return true if satisfied, false otherwise
	 */
	boolean test(T obj);
}
