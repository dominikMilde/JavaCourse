package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {
	@Test
	public void MultiplyTest() {
		ComplexPolynomial cp1 = new ComplexPolynomial(new Complex(2, 1));
		ComplexPolynomial cp2 = new ComplexPolynomial(new Complex(3, 1));

		ComplexPolynomial cp3 = cp1.multiply(cp2);
		ComplexPolynomial cp4 = cp2.multiply(cp1);
		String cp3String = "(5.00+i5.00)";
		assertEquals(cp3String, cp3.toString());
		assertEquals(cp3String, cp4.toString());
	}

	@Test
	public void OrderTest() {
		ComplexPolynomial cp1 = new ComplexPolynomial(new Complex(2, 1));
		ComplexPolynomial cp2 = new ComplexPolynomial(new Complex(3, 1));
		ComplexPolynomial cp3 = new ComplexPolynomial(new Complex(2, 1),
				new Complex(-2, 1));
		ComplexPolynomial cp4 = new ComplexPolynomial(new Complex(3, 1),
				new Complex(22, 1), new Complex(2, 0));

		assertEquals(0, cp1.order());
		assertEquals(0, cp2.order());
		assertEquals(1, cp3.order());
		assertEquals(2, cp4.order());
	}

	@Test
	public void DeriveTest1() {
		ComplexPolynomial cp1 = new ComplexPolynomial(new Complex(-2, 0),
				new Complex(0, 0), new Complex(0, 0), new Complex(0, 0),
				new Complex(2, 0));
		String cp1Derived = "(8.00+i0.00)*z^3+(0.00+i0.00)*z^2+(0.00+i0.00)*z^1+(0.00+i0.00)";

		assertEquals(cp1Derived, cp1.derive().toString());
	}

	@Test
	public void DeriveTest2() {
		ComplexPolynomial cp1 = new ComplexPolynomial(new Complex(-2, -7.22),
				new Complex(2.22, 11), new Complex(4, -3),
				new Complex(3, -1.1));
		String cp1Derived = "(9.00-i3.30)*z^2+(8.00-i6.00)*z^1+(2.22+i11.00)";

		assertEquals(cp1Derived, cp1.derive().toString());
	}
	 @Test
	    public void order() {
	        ComplexPolynomial polynomial = new ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2, 0),
	                new Complex(7, 2));

	        assertEquals(3, polynomial.order());
	    }

	    @Test
	    public void multiply() {
	        ComplexPolynomial polynomial = new ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2, 0),
	                new Complex(7, 2)).multiply(new ComplexPolynomial(new Complex(2, 2), new Complex(1, 1)));

	        Complex[] factors = polynomial.getFactors();

	        assertEquals(new Complex(2, 2), factors[0]);
	        assertEquals(new Complex(11, 11), factors[1]);
	        assertEquals(new Complex(9, 9), factors[2]);
	        assertEquals(new Complex(12, 20), factors[3]);
	        assertEquals(new Complex(5, 9), factors[4]);
	    }

	    @Test
	    public void derive() {
	        ComplexPolynomial polynomial = new ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2, 0),
	                new Complex(7, 2))
	                .derive();

	        Complex[] factors = polynomial.getFactors();

	        assertEquals(new Complex(5, 0), factors[0]);
	        assertEquals(new Complex(4, 0), factors[1]);
	        assertEquals(new Complex(21, 6), factors[2]);

	    }

	    @Test
	    public void apply() {
	        Complex c = new ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2, 0),
	                new Complex(7, 2)).apply(new Complex(1, 1));

	        assertEquals("(-12.00+i19.00)", c.toString());
	    }

	    @Test
	    public void getFactors() {
	        ComplexPolynomial polynomial = new ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2, 0),
	                new Complex(7, 2));

	        Complex[] factors = polynomial.getFactors();

	        assertEquals(new Complex(1, 0), factors[0]);
	        assertEquals(new Complex(5, 0), factors[1]);
	        assertEquals(new Complex(2, 0), factors[2]);
	        assertEquals(new Complex(7, 2), factors[3]);

	    }
}
