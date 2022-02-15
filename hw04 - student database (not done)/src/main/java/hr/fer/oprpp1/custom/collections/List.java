package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 * Represents specialized Collection and defines what methods classes who implement this must implement
 */
public interface List<T> extends Collection<T>{
	/**
	 * Returns object on index from List
	 * @param index Position in List
	 * @return Object on position index
	 */
	T get(int index);
	/**
	 * returns object at given index
	 * @param value Object to insert
	 * @param position Index to insert to
	 */
	void insert(T value, int position);
	/**
	 * Inserts object to list
	 * @param value object we search for
	 * @return index of object in List
	 */
	int indexOf(Object value);
	/**
	 * Removes object on index index from List
	 * @param index 
	 */
	void remove(int index);
}
