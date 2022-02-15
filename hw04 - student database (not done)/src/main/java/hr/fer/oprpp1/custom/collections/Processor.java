package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 * Processor is a model of an object capable of performing some operation on the passed object
 */
public interface Processor<T> {
	
	/**
	 * implementation is left for every individual Processor
	 * @param value "data" that is being processed
	 */
	void process(T value);
}
