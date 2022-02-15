package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

public class DemoEvenTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));

	}

}
