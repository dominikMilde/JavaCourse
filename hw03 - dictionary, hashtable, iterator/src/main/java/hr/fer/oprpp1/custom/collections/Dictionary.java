package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * @author Dominik
 *
 * Implementation of key, value dictionary with parameterized types
 * @param <K> key
 * @param <V> value
 */
public class Dictionary <K, V>{
	/**
	 * @author Dominik
	 * represents one entry in dictionary
	 * @param <K>
	 * @param <V>
	 */
	@SuppressWarnings("hiding")
	private class Entry<K, V>{
		/**
		 * key of entryy
		 */
		private K key;
		/**
		 * value of entry
		 */
		private V value;
		
		/**
		 * constructor
		 * @param key
		 * @param value
		 */
		public Entry(K key, V value) {
			if (key == null) throw new NullPointerException("Key can't be null!");
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
		 * setter method, changes value of entry
		 * @param value this will be written
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}
	/**
	 * @author Dominik
	 * implementation of iterator for iterating dictionary
	 * @param <T> parameterized type
	 */
	private static class DictionaryIterator<T> implements ElementsGetter<T>{
		/**
		 * reference to internal array
		 */
		ArrayIndexedCollection<T> arrayRef;
		/**
		 * internal index
		 */
		int index;
		//long savedModificationCount;
		public DictionaryIterator(ArrayIndexedCollection<T> array) {
			this.arrayRef = array;
			this.index = 0;
		}
		/**
		 * if has next element returns true
		 */
		@Override
		public boolean hasNextElement() {
			if(index < arrayRef.size()) return true;
			return false;
		}
		/**
		 * returns next element
		 */
		@Override
		public T getNextElement() {
			if(hasNextElement()) {
				index += 1;
				return arrayRef.get(index-1);
			}
			throw new NoSuchElementException("Can't get next element because you got all elements.");
		}
	}
	
	
	/**
	 * internal storage for dictionary
	 */
	ArrayIndexedCollection<Entry<K, V>> array;
	
	/**
	 * creates ArrayIndexedCollection
	 */
	public Dictionary() {
		this.array = new ArrayIndexedCollection<Entry<K, V>>();
	}
	
	/**
	 * @return true if dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	/**
	 * @return size of dictionary
	 */
	public int size() {
		return array.size();
	}
	/**
	 * removes all elements of dictionary
	 */
	public void clear() {
		array.clear();
	}
	/**
	 * Puts entry in dictionary
	 * @param key Key of entry
	 * @param value Value for Key
	 * @return value
	 */
	public V put(K key, V value) {
		if(get(key) == null) {
			array.add(new Entry<K, V>(key, value));
		}
		else {
			DictionaryIterator<Entry<K, V>> iterator = new DictionaryIterator<>(this.array);
			while(iterator.hasNextElement()) {
				Entry <K, V> entry = iterator.getNextElement();
				if (entry.getKey().equals(key)) {
					entry.setValue(value);
				}
			}
		}
		return value;
	} // "gazi" eventualni postojeæi zapis
	/**
	 * @param key Key we want to search value that belongs to
	 * @return value for Key
	 */
	public V get(Object key) {
		DictionaryIterator<Entry<K, V>> iterator = new DictionaryIterator<>(this.array);
		while(iterator.hasNextElement()) {
			Entry <K, V> entry = iterator.getNextElement();
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	} // ako ne postoji pripadni value, vraæa null
	/**
	 * Removes entry from dictionary
	 * @param key Key of removed entry
	 * @return value of removed, null if not removed
	 */
	public V remove(K key) {
		DictionaryIterator<Entry<K, V>> iterator = new DictionaryIterator<>(this.array);
		while(iterator.hasNextElement()) {
			Entry <K, V> entry = iterator.getNextElement();
			if (entry.getKey().equals(key)) {
				V valueToReturn = entry.getValue();
				array.remove(entry);
				return valueToReturn;
			}
		}
		return null;
	} // uklanja zapis za kljuè (ako postoji)

}
