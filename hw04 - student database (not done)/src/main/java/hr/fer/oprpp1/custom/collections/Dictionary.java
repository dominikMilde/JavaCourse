package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

public class Dictionary <K, V>{
	@SuppressWarnings("hiding")
	private class Entry<K, V>{
		private K key;
		private V value;
		
		public Entry(K key, V value) {
			if (key == null) throw new NullPointerException("Key can't be null!");
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return this.key;
		}
		
		public V getValue() {
			return this.value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	private static class DictionaryIterator<T> implements ElementsGetter<T>{
		ArrayIndexedCollection<T> arrayRef;
		int index;
		//long savedModificationCount;
		public DictionaryIterator(ArrayIndexedCollection<T> array) {
			this.arrayRef = array;
			this.index = 0;
		}
		@Override
		public boolean hasNextElement() {
			if(index < arrayRef.size()) return true;
			return false;
		}
		@Override
		public T getNextElement() {
			if(hasNextElement()) {
				index += 1;
				return arrayRef.get(index-1);
			}
			throw new NoSuchElementException("Can't get next element because you got all elements.");
		}
	}
	ArrayIndexedCollection<Entry<K, V>> array;
	
	public Dictionary() {
		this.array = new ArrayIndexedCollection<Entry<K, V>>();
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	public int size() {
		return array.size();
	}
	public void clear() {
		array.clear();
	}
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
