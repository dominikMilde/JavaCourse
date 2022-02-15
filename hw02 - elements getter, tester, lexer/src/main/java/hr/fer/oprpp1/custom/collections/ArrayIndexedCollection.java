package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author Dominik
 * Specialization of class Collection, storage in array of objects
 */
public class ArrayIndexedCollection implements List{
	private static class ArrayIndexedElementsGetter implements ElementsGetter{
		ArrayIndexedCollection array;
		int index;
		long savedModificationCount;
		public ArrayIndexedElementsGetter(ArrayIndexedCollection array) {
			this.array = array;
			this.index = 0;
			this.savedModificationCount = array.modificationCount;
		}
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != array.modificationCount) throw new ConcurrentModificationException("Collection has been modified!");
			if(index < array.size) return true;
			return false;
		}
		@Override
		public Object getNextElement() {
			if(savedModificationCount != array.modificationCount) throw new ConcurrentModificationException("Collection has been modified!");
			if(hasNextElement()) {
				index += 1;
				return array.get(index-1);
			}
			throw new NoSuchElementException("Can't get next element because you got all elements.");
		}
	}
	private long modificationCount = 0;
	/**
	 * current size of collection (number of elements actually stored in elements array)
	 */
	private int size = 0;
	/**
	 *  an array of object references
	 */
	private Object[] elements;
	
	/**
	 * Constructor with single parameter, preallocates the elements array of size initialCapacity
	 * @param initalCapacity 
	 * @throws IllegalArgumentException  If initial capacity is less then 1, an
IllegalArgumentException should be thrown.
	 */
	public ArrayIndexedCollection(int initalCapacity) {
		if(initalCapacity < 1) throw new IllegalArgumentException("Initial capacity must be greater than 1, it was: " + initalCapacity + ".");
		
		elements = new Object[initalCapacity];
	}
	
	/**
	 * The default constructor  creates an instance with
capacity set to 16 (this also means that constructor preallocates the elements array of that size)
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * @param collection  a non-null reference to some other Collection which elements are copied into this newly
constructed collection
	 * @param initialCapacity  if the initialCapacity is smaller than the size of the given collection, the size of
the given collection should be used for elements array preallocation
	 *@throws NullPointerException   If the given collection is null, a
NullPointerException should be thrown
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if(collection == null) throw new NullPointerException("Collection argument can't be null!");
		if(initialCapacity < 1) throw new IllegalArgumentException("Initial capacity must be greater than 1, it was: " + initialCapacity + ".");
		
		if(initialCapacity < collection.size()) {
			elements = new Object[collection.size()];
		}
		else {
			elements = new Object[initialCapacity];
		}
		addAll(collection);
	}
	/**
	 * Second parameter is implicitly 16. This constructor calls Constructor with two parameters
	 * @param collection a non-null reference to some other Collection which elements are copied into this newly
constructed collection
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
	}
	
	
	/**
	 *Returns the number of currently stored objects in this array.
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 *Adds the given object into this collection (reference is added into first empty place in the elements array;
if the elements array is full, it should be reallocated by doubling its size).
	 *@throws NullPointerException The method should refuse to
add null as element 
	 *@param value reference to Object that is being added
	 */
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException("You can't pass null to this method!");
		
		if(elements.length <= size){
			Object [] newElements = new Object[elements.length * 2];
			modificationCount++;
			for(int i=0; i<elements.length; i++) {
				newElements[i] = elements[i];
			}
			elements = newElements;
			newElements = null;
		}
		elements[size] = value;
		size++;
	}
	
	/**
	 * Returns the object that is stored in backing array at position index. Valid indexes are 0 to size-1.
	 * @param index Index at we search for Object to return
	 * @return Object at position index in current Array
	 * @throws IndexOutOfBoundsException Valid indexes are 0 to size-1
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) throw new IndexOutOfBoundsException("Can't reach object on index: " + index + " as valid range is [0-" + (size-1) + "]");
		
		return elements[index];
	}
	/**
	 * Inserts (does not overwrite) the given value at the given position in array (observe that before actual
insertion elements at position and at greater positions must be shifted one place toward the end, so that an
empty place is created at position).
	 * @param value reference to Object that is inserted
	 * @param position Position to which value will be added
	 * @throws NullPointerException if value is null
	 * @throws IndexOutOfBoundsException The legal positions are 0 to size (both are included)
	 */
	public void insert(Object value, int position) {
		if(value == null) throw new NullPointerException("You can't insert null value!");
		if(position < 0 || position > size) throw new IndexOutOfBoundsException("Can't insert object on index: " + position + " as valid range is [0-" + (size) + "]");
		
		if(position < size) modificationCount++;
		if(elements.length <= size){
			Object [] newElements = new Object[elements.length * 2];
			for(int i=0; i<elements.length; i++) {
				newElements[i] = elements[i];
			}
			elements = newElements;
			newElements = null;
		}
		
		for(int i=size; i>position; i--) {
			elements[i] = elements[i-1]; 
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Returns true only if the collection contains given value, as determined by equals method.
	 * @param value Object we search for
	 * @return boolean true only if the collection contains given value
	 */
	 
	public boolean contains(Object value) {
		if(indexOf(value) != -1) return true;
		return false;
	} 
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
not found. Argument can be null and the result must be that this element is not found (since the collection
can not contain null). The equality should be determined using the equals method.
	 * @param value what we search for
	 * @return  index of the first occurrence of the given value or -1 if the value is
not found
	 */
	public int indexOf(Object value) {
		int index = -1;
		for(int i=0; i<size; i++) {
			if(value.equals(elements[i])) {
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	/**
	 * Removes element at specified index from collection. Element that was previously at location index+1
after this operation is on location index
	 * @param index index of object we want to remove from array
	 * @throws IndexOutOfBoundsException Legal indexes are 0 to size-1
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) throw new IndexOutOfBoundsException("Can't remove object on index: " + index + " as valid range is [0-" + (size-1) + "]");
		
		for(int i = index; i<size-1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size-1] = null;
		size--;
		modificationCount++;
	}
	
	/**
	 *Returns true only if the collection contains given value as determined by equals method and removes
one occurrence of it. Internally calls void remove(int index);
	 * @param value Object we want to remove
	 * @return true if successful, false otherwise 
	 */
	@Override
	public boolean remove(Object value) {
		int indexToRemove = indexOf(value);
		if(indexToRemove == -1) return false;
		remove(indexToRemove);
		return true;
	}
	/**
	 *Allocates new array with size equals to the size of this collections, fills it with collection content and
returns the array. This method never returns null.
	 * @return new array filled with values from current Array
	 */
	@Override
	public Object[] toArray() {
		Object[] arrayToReturn = new Object[size];
		for(int i=0; i<size; i++) {
			arrayToReturn[i] = elements[i];
		}
		return arrayToReturn;
		
	}

	/**
	 *Removes all elements from this ArrayIndexedCollection
	 */
	@Override
	public void clear() {
		for(int i=0; i<elements.length; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	@Override
	public ArrayIndexedElementsGetter createElementsGetter() {
		ArrayIndexedElementsGetter e = new ArrayIndexedElementsGetter(this);
		return e;
	}
	
}
