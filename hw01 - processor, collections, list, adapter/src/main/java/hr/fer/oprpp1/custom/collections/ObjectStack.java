package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 * Simluating stack storage with internal ArrayIndexedCollection
 */
public class ObjectStack {
	private ArrayIndexedCollection storage;
	
	/**
	 * Default constructor, initializes new {@link ArrayIndexedCollection} size 16 
	 */
	public ObjectStack() {
		this.storage = new ArrayIndexedCollection();
	}
	
	/**
	 * @return true of stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}
	
	/**
	 * @return size of stack
	 */
	public int size() {
		return storage.size();
	}
	
	/**
	 * Method pushes to stack
	 * @param value Object that is pushed
	 */
	public void push(Object value) {
		storage.add(value);
	}
	/**
	 * removes object from top of the stack and returns that object
	 * @return Object on the top of stack
	 * @throws EmptyStackException if user wants to pop from empty stack
	 */
	public Object pop() {
		if(isEmpty()) throw new EmptyStackException("Can't pop from empty stack");
		Object objReturn = storage.get(size()-1);
		storage.remove(size()-1);
		return objReturn;
	}
	
	/**
	 * returns that object from top of the stack, doesn't remove it
	 * @return object from top of the stack
	 * @throws EmptyStackException if user wants to pop from empty stack
	 */
	public Object peek() {
		if(isEmpty()) throw new EmptyStackException("Can't peek into empty stack");
		return (storage.get(size()-1));
	}
	/**
	 * Clears stack, internally uses ArrayIndexedCollection::clear()
	 */
	public void clear() {
		storage.clear();
	}
}
