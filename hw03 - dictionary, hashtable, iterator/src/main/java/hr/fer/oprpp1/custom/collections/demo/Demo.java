package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        
        System.out.println(testTable.toString());
	}

}
