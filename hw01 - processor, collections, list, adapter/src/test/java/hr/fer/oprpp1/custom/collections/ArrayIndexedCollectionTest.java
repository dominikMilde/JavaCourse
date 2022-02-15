package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	public ArrayIndexedCollection generateArray() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(3);
		a.add(1);
		a.add("dominik");
		a.add(true);
		return a;
	}
	
	/**
	 * We will use this Test in other tests so that is why we check this first
	 */
	@Test
	public void testToArray() {
		ArrayIndexedCollection a = generateArray();
		Object [] expected = new Object[] {1, "dominik", true};
		Assertions.assertArrayEquals(expected, a.toArray());
		
	}
	
	@Test
	public void testConstructorIllegalCapacity() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {ArrayIndexedCollection a = new ArrayIndexedCollection(0);});
	}
	
	@Test
	public void testConstructorIllegalCollection() {
		
		Assertions.assertThrows(NullPointerException.class, () -> {ArrayIndexedCollection a = new ArrayIndexedCollection(null);});
	}
	
	@Test
	public void testAddWithDoublingCapacity() {
		ArrayIndexedCollection a = generateArray();
		a.add(1);
		Object [] expected = new Object[] {1, "dominik", true, 1};
		Assertions.assertArrayEquals(expected, a.toArray());
	}
	
	@Test
	public void testConstructorWithCollectionParam() {
		ArrayIndexedCollection a = generateArray();
		ArrayIndexedCollection b = new ArrayIndexedCollection(a);
		b.add(1);
		Object [] expected = new Object[] {1, "dominik", true, 1};
		Assertions.assertArrayEquals(expected, b.toArray());
	}
	
	@Test
	public void testSize() {
		ArrayIndexedCollection a = generateArray();
		Assertions.assertEquals(3, a.size());
		a.add(1);
		a.add("4");
		Assertions.assertEquals(5, a.size());
	}
	
	@Test
	public void testGetIllegalIndex() {
		ArrayIndexedCollection a = generateArray();
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(-1);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.get(3);});
	}
	@Test
	public void testInsertNullAndWrongIndex() {
		ArrayIndexedCollection a = generateArray();
		Assertions.assertThrows(NullPointerException.class, () -> {a.insert(null, 0);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.insert("a", -1);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.insert("a", 4);});
	}
	
	@Test
	public void testInsertGoodUse() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(5);
		a.insert(1, 0);
		a.add(2);
		a.insert(3, 1);
		a.insert(4, 3);
		a.insert(0, 0);
		Object [] expected = new Object[] {0, 1, 3, 2, 4};
		Assertions.assertArrayEquals(expected, a.toArray());
	}
	
	@Test
	public void testContains() {
		ArrayIndexedCollection a = generateArray();
		Assertions.assertEquals(true, a.contains("dominik"));
		Assertions.assertEquals(false, a.contains(5));
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection a = generateArray();
		Assertions.assertEquals(-1, a.indexOf("milde"));
		Assertions.assertEquals(0, a.indexOf(1));
	}
	
	@Test
	public void removeByIndexWrongUse() {
		ArrayIndexedCollection a = generateArray();
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.remove(-1);});
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {a.remove(3);});
	}
	
	@Test
	public void removeByIndexGoodUse() {
		ArrayIndexedCollection a = generateArray();
		a.remove(0);
		Object [] expected = new Object[] {"dominik", true};
		Assertions.assertArrayEquals(expected, a.toArray());
		a.add(2);
		a.remove(1);
		expected = new Object[] {"dominik", 2};
		Assertions.assertArrayEquals(expected, a.toArray());
		a.remove(1);
		expected = new Object[] {"dominik"};
		Assertions.assertArrayEquals(expected, a.toArray());
	}
	
	@Test
	public void testRemoveByValue() {
		ArrayIndexedCollection a = generateArray();
		a.remove("dominik");
		Object [] expected = new Object[] {1, true};
		Assertions.assertArrayEquals(expected, a.toArray());
	}
	
	@Test
	public void testForEachImplicitlyAndConstructorWithOtherCollection() {
		ArrayIndexedCollection a = generateArray();
		ArrayIndexedCollection sameSize = new ArrayIndexedCollection(a, 3);
		Object [] expected = new Object[] {1,"dominik",  true};
		Assertions.assertArrayEquals(expected, sameSize.toArray());
		sameSize.add("Added");
		ArrayIndexedCollection bigger = new ArrayIndexedCollection(sameSize, 2);
		expected = new Object[] {1,"dominik",  true, "Added"};
		Assertions.assertArrayEquals(expected, bigger.toArray());
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection a = generateArray();
		a.clear();
		Assertions.assertEquals(0, a.size());
	}
	
	@Test
	public void testAddAll() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(1);
		ArrayIndexedCollection b = generateArray();
		a.insert("0", 0);
		a.addAll(b);
		Object [] expected = new Object[] {"0" ,1, "dominik",  true};
		Assertions.assertArrayEquals(expected, a.toArray());
	}
}
