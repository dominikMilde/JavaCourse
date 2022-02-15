package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author Dominik
 * implementation of linked list-backed collection of objects denoted as
LinkedListIndexedCollection which extends class Collection
 */
public class LinkedListIndexedCollection<T> implements List<T>{
	private static class LinkedListIndexedElementsGetter<T> implements ElementsGetter<T>{
		/**
		 * reference to list we iterate on
		 */
		LinkedListIndexedCollection<T> list;
		/**
		 *  current index
		 */
		int index;
		/**
		 * counts modifications
		 */
		long savedModificationCount;
		/**
		 * @param list reference to list 
		 */
		public LinkedListIndexedElementsGetter(LinkedListIndexedCollection<T> list) {
			this.list = list;
			this.index = 0;
			this.savedModificationCount = list.modificationCount;
		}
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != list.modificationCount) throw new ConcurrentModificationException("Collection has been modified!");
			if(index < list.size) return true;
			return false;
		}
		@Override
		public T getNextElement() {
			if(savedModificationCount != list.modificationCount) throw new ConcurrentModificationException("Collection has been modified!");
			if(hasNextElement()) {
				index += 1;
				return list.get(index-1);
			}
			throw new NoSuchElementException("Can't get next element because you got all elements.");
		}
	}
	/**
	 * @author Dominik
	 * Implementation of one node in LInkedList
	 * private static class ListNode with pointers to previous and next list node and
additional reference for value storage
	 */
	private static class ListNode<T>{
		T value;
		ListNode<T> previous;
		ListNode<T> next;
		
		/**
		 * 
		 * @param value sets value of this node
		 */
		public ListNode(T value) {
			this.value = value;
		}
	}
	private long modificationCount = 0;
	/**
	 * Size of LinkedList
	 */
	private int size;
	/**
	 * Pointer to first node
	 */
	private ListNode<T> first;
	/**
	 * Pointer to last node
	 */
	private ListNode<T> last;
	/**
	 * The default constructor  creates an empty collection with
first=last=null.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	/**
	 *  constructor should have a single parameter: reference to some other
Collection whose elements are copied into this newly constructed collection.

	 * @param other Collection we copy from. 
	 */
	public LinkedListIndexedCollection(Collection<T> other) {
		if(other == null) throw new NullPointerException("You can't pass null!");
		
		addAll(other);
	}
	/**
	 *@return Returns the number of currently stored objects in this collections.
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 *Adds the given object into this collection at the end of collection; newly added element becomes the element at the biggest index
	*@throws NullPointerException The method should refuse to add null as element 
	 */
	@Override
	public void add(T value) {
		if(value == null) throw new NullPointerException("You can't add null!");
		
		modificationCount++;
		ListNode<T> newListNode = new ListNode<T>(value);
		if(first == null) { //LIST is empty
			first = newListNode;
			last = newListNode;
			
			first.previous = null;
			last.next = null;     //equals nLN.next = null;
		}
		else { //is not empty, must be "rewired"
			last.next = newListNode;
			newListNode.previous = last;
			last = newListNode;
			newListNode.next = null; //equals last.next = null;
		}
		size++;
	}
	/**
	 *Returns true only if the collection contains given value, as determined by equals method.
	 * @param value Object we search for
	 * @return boolean
	 */
	public boolean contains(Object value) {
		if(indexOf(value) != -1) return true;
		return false;
	} 
	/**
	 * Returns the object that is stored in linked list at position index. Valid indexes are 0 to size-1. If index is
invalid, the implementation should throw the appropriate exception (IndexOutOfBoundsException). 
	 * @param index
	 * @return object that is stored in linked list at position index
	 * @throws IndexOutOfBoundsException if index invalid
	 */
	/**
	 * @param index
	 * @return
	 */
	public T get(int index) {
		if(index < 0 || index > size-1) throw new IndexOutOfBoundsException("Can't reach object on index: " + index + " as valid range is [0-" + (size-1) + "]");
		
		ListNode<T> temp;
		if(index < size/2.0) {
			//go from first
			temp = first;
			for(int i = 0; i<index; i++) {
				temp = temp.next;
			}
		}
		else {
			//go from last
			temp = last;
			int numberOfSteps = size - index - 1;
			for(int i=0; i<numberOfSteps; i++) {
				temp = temp.previous;
			}
		}
		return temp.value;
	}
	/**
	 * Inserts (does not overwrite) the given value at the given position in linked-list. Elements starting from
this position are shifted one position. The legal positions are 0 to size
	 * @param value Value to insert
	 * @param position Position to insert into.
	 * @throws IndexOutOfBoundsException if index invalid
	 * @throws NullPointerException if null is passed as value
	 * 
	 */
	public void insert(T value, int position) {
		if(position < 0 || position > size) throw new IndexOutOfBoundsException("Can't insert object on index: " + position + " as valid range is [0-" + (size) + "]");
		if(value == null) throw new NullPointerException("You can't insert null!");
		
		
		modificationCount++;
		ListNode<T> newNode = new ListNode<T>(value);
		if(first == null) { //nothing in list
			newNode.next = null;
			first = newNode;
			last = newNode;
			newNode.previous = null;
		}
		else if(position == size) { //add on end
			newNode.next = null;
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
		}
		else if(position != 0){ //add in the middle
			ListNode<T> temp = first;
			for(int i=0; i<position; i++) {
				temp = temp.next;
			}
			newNode.next = temp;
			ListNode<T> beforeTemp = temp.previous;
			temp.previous = newNode;
			beforeTemp.next = newNode;
			newNode.previous = beforeTemp;
		}
		else { //add in the first place but not empty before
			newNode.previous = null;
			newNode.next = first;
			first.previous = newNode;
			first = newNode; 
		}
		size++;
	}
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
not found. null is valid argument. The equality should be determined using the equals method
	 * @param value 
	 * @return Index of the first occurrence of the given value or -1 if the value is not found
	 */
	public int indexOf(Object value) {
		int index = -1;
		ListNode<T> temp = first;
		for(int i=0; i<size; i++) {
			if(value.equals(temp.value)) {
				index = i;
				break;
			}
			temp=temp.next;
		}
		
		return index;
	}
	
	/**
	 * Returns true only if the collection contains given value as determined by equals method and removes
one occurrence of it
	 * @param value Object we want to remove
	 * @return
	 */
	@Override
	public boolean remove(Object value) {
		int indexToRemove = indexOf(value);
		if(indexToRemove == -1) return false;
		remove(indexToRemove);
		return true;
	}
	/**Removes element at specified index from collection. Element that was previously at location index+1
after this operation is on location index, etc. Legal indexes are 0 to size-1
	 * @param index
	 * @throws IndexOutOfBoundsException if index is in invalid range
	 * @throws RuntimeException if user wants to remove from empty list
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) throw new IndexOutOfBoundsException("Can't remove object on index: " + index + " as valid range is [0-" + (size-1) + "]");
		if(first == null) throw new RuntimeException("Can't remove from empty list.");
		
		modificationCount++;
		if(index == 0) {
			if(first.next == null) { //list will be empty
				last = null;
			}
			else {
				first.next.previous = null;
			}
			first = first.next;
		}
		else if(index == size-1){
			if(first.next == null) {
				first = null; //only one Node
			}
			else {
				last.previous.next = null;
			}
			last = last.previous; //must happen for all cases
		}
		else {
			ListNode<T> temp = first;
			for(int i=0; i<index; i++) {
				temp = temp.next;
			}
			temp.previous.next = temp.next;
			temp.next.previous = temp.previous;
		}
		size--;
		
	}
	/**
	 *Allocates new array with size equals to the size of this collections, fills it with collection content and
returns the array. This method never returns null.
	 * @return new array filled with values from current collection
	 */
	@Override
	public Object[] toArray() {
		Object[] arrayToReturn = new Object[size];
		ListNode<T> start = first;
		for(int i=0; i<size; i++) {
			arrayToReturn[i] = start.value;
			start = start.next;
		}
		
		return arrayToReturn;
	}
	
	/**
	 * Removes all elements from this collection.
	 * Sets first, last = null
	 */
	@Override
	public void clear() {
		modificationCount++;
		size = 0;
		first = null;
		last = null;
	}
	@Override
	public LinkedListIndexedElementsGetter<T> createElementsGetter() {
		LinkedListIndexedElementsGetter <T> e = new LinkedListIndexedElementsGetter<T>(this);
		return e;
		
	}
}
