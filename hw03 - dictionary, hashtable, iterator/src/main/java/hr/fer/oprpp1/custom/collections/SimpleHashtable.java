package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Dominik
 *
 * Implementation of HashTable with parameterized type
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	/**
	 * @author Dominik
	 * Implementation of entry in hashtable
	 * @param <K>
	 * @param <V>
	 */
	static class TableEntry<K,V>{
		/**
		 * key of entry
		 */
		K key;
		/**
		 * value of entry
		 */
		V value;
		/**
		 * reference to next entry
		 */
		TableEntry<K, V> next = null;
		
		/**
		 * contructor, initializes value and key of entry 
		 * @param key key of entry
		 * @param value value of entry
		 * @throws IllegalArgumentException if key is null
		 */
		public TableEntry(K key, V value){
			if(key == null) throw new IllegalArgumentException("key cannot be null!");
			this.key = key;
			this.value = value;
		}
		/**
		 * getter method
		 * @return key of entry
		 */
		public K getKey() {
			return this.key;
		}
		/**
		 * getter method
		 * @return value of entry
		 */
		public V getValue() {
			return this.value;

		}
		/**
		 * setter method
		 * @param value Value that will be written
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * returns string representation of entry
		 */
		public String toString() {
			return key.toString() + " : " + value.toString();
		}
		
	}
	
	/**
	 * number of slots
	 */
	private int slots;
	/**
	 * number of elements in hashtabke
	 */
	private int size = 0;
	/**
	 * array of references to first entry in "list"
	 */
	private TableEntry<K, V>[] table;
	/**
	 * counts number of changes
	 */
	private int modificationCount = 0;
	
	/**
	 * Creates hashtable with given with number of slots that is  power of two  first bigger than "slots" argument
	 * @param slots wanted number of slots
	 * @throws IllegalArgumentException if given argument is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int slots) {
		if(slots < 1) throw new IllegalArgumentException("Slot number must be at least one!");
		int pot = 2;
		if(slots <= pot) {
			this.table = (TableEntry<K, V>[]) new TableEntry[pot];
			this.slots = slots;
			return;
		}
		else {
			pot = pot * 2;
		}
	}
	/**
	 * default, creates hashtable with 16 slots
	 */
	public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * puts entry in hashtable, if hashtable is filled above some degree, will double in size
	 * @param key key to put
	 * @param value value to put
	 * @return value of entry
	 * @throws NullPointerException if key is null
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("key cannot be null!");
		
		if(size >= 0.75*table.length) {
			//System.out.println("I will resize!");
			TableEntry<K, V>[] prevTable = table;
			
			this.table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
			this.slots = table.length;
			this.size = 0;
			
			for(int i = 0; i<prevTable.length; i++) {
				TableEntry<K, V> tempForCopying = prevTable[i];
				while(tempForCopying != null) {
					put(tempForCopying.getKey(), tempForCopying.getValue());
					tempForCopying = tempForCopying.next;
				}
			}
			prevTable = null;
		}
		
		int slot = Math.abs(key.hashCode()) % slots; //could be changed
		TableEntry<K, V> temp = table[slot];
		TableEntry<K, V> previous = table[slot];
		
		while(temp != null) {
			if(temp.getKey().equals(key)) break;
			previous = temp;
			temp = temp.next;
		}
		
		if(temp != null) {
			//we change value, not adding
			temp.setValue(value);
			return value;
		}
		
		else {
			TableEntry<K, V> newEntry = new TableEntry<>(key, value);
			
			
			if(previous == null) table[slot] = newEntry;
			else {
				previous.next = newEntry;
			}
					
			size++;
			modificationCount++;
			return null;
		}
	}
	/**
	 * @param key key to search for
	 * @return Value for given key, null if key doesn't exist
	 */
	public V get(Object key) {
		int slot = Math.abs(key.hashCode()) % slots;
		
		TableEntry<K, V> temp = table[slot];
		while(temp != null) {
			if(key.equals(temp.getKey())) {
				return temp.getValue();
			}
			temp = temp.next;
		}
		return null;
	}
	/**
	 * @return number of elements in hashtable
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * @param key Key we look for
	 * @return true if entry with given key is in hashtable, false otherwise
	 */
	public boolean containsKey(Object key) {
		int slot = Math.abs(key.hashCode()) % slots;
		
		TableEntry<K, V> temp = table[slot];
		while(temp != null) {
			if(key.equals(temp.getKey())) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	/**
	 * @param value Value we look for
	 * @return true if entry with given value is in hashtable, false otherwise
	 */
	public boolean containsValue(Object value) {
		for(int slot = 0; slot < this.slots; slot++) {
			TableEntry<K, V> temp = table[slot];
			while(temp != null) {
				if(value.equals(temp.getValue())) {
					return true;
				}
				temp = temp.next;
			}
		}
		return false;
	}
	
	/**
	 * removes entry from hashtable
	 * @param key key of entry to remove
	 * @return value of removed key, null if not removed
	 */
	public V remove(Object key) {
		int slot = Math.abs(key.hashCode()) % slots;
		TableEntry<K, V> temp = table[slot];
		TableEntry<K, V> previous = table[slot];
		while(temp != null) {
			if(temp.getKey().equals(key)) {
				if(temp == table[slot])
					table[slot] = temp.next;
				else 
					previous.next = temp.next;
				size--;
				modificationCount++;
				return temp.getValue();
			}
			previous = temp;
			temp = temp.next;
		}
		return null;
	}
	
	/**
	 * @return true if hashtable is empty, false otherwise
	 */
	public boolean isEmpty() {
		if(this.size == 0) return true;
		return false;
	}
	/**
	 * returns string representation of hashtable
	 */
	public String toString() {
		//System.out.println("usao");
		String out = "[";
		//System.out.println(out);
		for(int slot = 0; slot < this.slots; slot++) {
			TableEntry<K, V> temp = table[slot];
			while(temp != null) {
				out += temp.getKey() + "=" + temp.getValue() + ", ";
				temp = temp.next;
			}
		}
		//System.out.println(out);
		out = out.substring(0, out.length()-2) + "]";
		return out;
	}
	/**
	 * deletes all entries
	 */
	public void clear() {
		for(int slot = 0; slot < this.slots; slot++) {
			table[slot] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	/**
	 * creates Iterator
	 */
	@Override
	public IteratorImpl iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * @author Dominik
	 * implementation of Iterator for HashTable
	 */
	class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		/**
		 * internal variable for current slot
		 */
		private int currentSlot = 0;
		/**
		 * internal variable for current element
		 */
		private TableEntry<K, V> currentElement = table[0];
		/**
		 * internal counter
		 */
		private int iteratorCounter = 0;
		/**
		 * this will enable usage of remove method without problems
		 */
		private boolean removed = true;
		/**
		 * reference we will pass to remove method
		 */
		private TableEntry<K, V> entryToRemove;
		/**
		 * internal count, set to outer modificationCount
		 */
		private int internalModCount = modificationCount;
		
		/**
		 * returns true if next element exists
		 * @throws ConcurrentModificationException if changes were made from outsize
		 */
		public boolean hasNext() {
			if(internalModCount != modificationCount) throw new ConcurrentModificationException("Hashtable was modified!");
			
			if(iteratorCounter < size) return true;
			return false;
		}
		
		/**
		 *returns next element, resets removed to false
		 *@throws ConcurrentModificationException if changes were made from outsize
		 *@throws NoSuchElementException if can't reach next element
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if(internalModCount != modificationCount) throw new ConcurrentModificationException("Hashtable was modified!");
			
			removed = false;
			if(!hasNext()) throw new NoSuchElementException();
			
			while(currentElement == null) {
				currentElement = table[currentSlot];
				currentSlot++;
				//if(currentSlot == table.length) throw new IllegalArgumentException();
			}
			
			TableEntry<K, V> nextElement = currentElement;
			currentElement = currentElement.next;
			iteratorCounter++;
			entryToRemove = nextElement;
			return nextElement;
		}
		
		/**
		 * removes current entry, can only be called once after next()
		 * @throws IllegalStateException if called more than once between next()
		 */
		public void remove() {
			if (removed) throw new IllegalStateException("Cannot remove same element 2 times!");
			
			SimpleHashtable.this.remove(entryToRemove.getKey());
			internalModCount = modificationCount; //allow remove from this method
			iteratorCounter--;
			removed = true;
		}
	}
}
