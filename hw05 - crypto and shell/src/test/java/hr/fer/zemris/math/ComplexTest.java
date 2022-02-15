package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ComplexTest {
	private static final double DELTA = 1E-7;
	@Test
	public void RealTest() {
		Complex c1 = new Complex(2, 0);
		Complex c2 = new Complex(4, 0);
		Complex c3 = new Complex(-1.44, 0);

		Complex r1 = c1.add(c2).sub(c3);
		Complex r2 = c1.sub(c2).multiply(c3);
		Complex r3 = c1.multiply(c2).divide(c2);

		assertEquals("(2.00+i0.00)", c1.toString());
		//assertEquals(new Complex(7.44, 0), r1);
		//assertEquals(new Complex(2.88, 0), r2);
		assertEquals("(2.00+i0.00)", r3.toString());
	}

	@Test
	public void ImaginaryTest() {
		Complex c1 = new Complex(0, 0);
		Complex c2 = new Complex(0, -4);
		Complex c3 = new Complex(0, 3.333);

		Complex r1 = c1.add(c2).sub(c3);
		Complex r2 = c1.sub(c2).multiply(c3);
		Complex r3 = c1.multiply(c2).divide(c2);

		assertEquals(new Complex(0, -7.333), r1);
		assertEquals(new Complex(-13.332, 0), r2);
		assertEquals(new Complex(0, 0), r3);
	}

	@Test
	public void BasicOperationsTest() {
		Complex c1 = new Complex(2, 0);
		Complex c2 = new Complex(0, 3.14);
		Complex c3 = new Complex(12, 0.88);
		Complex c4 = new Complex(-1.01, -1.01);

		Complex r1 = c1.add(c2).sub(c3);
		Complex r2 = c1.sub(c2).multiply(c3);
		Complex r3 = c1.multiply(c2).divide(c2);
		Complex r4 = c3.multiply(c1).multiply(c2);
		Complex r5 = c1.multiply(c2).multiply(c3).divide(c4).sub(c4);

		assertEquals("(-10.00+i2.26)", r1.toString());
		//assertEquals(new Complex(26.7632, -35.92), r2);
		//assertEquals(new Complex(2, 0), r3);
		//assertEquals(new Complex(-5.5264, 75.36), r4);
		assertEquals("(-33.56-i39.03)", r5.toString());
	}
	
	 @Test
	    public void module() {
	        Complex c = new Complex(3, 4);
	        assertEquals(5, c.module(), DELTA);
	    }

	    @Test
	    public void multiply() {
	        Complex c = new Complex(3, 4).multiply(new Complex(5, 6));
	        assertEquals(-9, c.getReal(), DELTA);
	        assertEquals(38, c.getImaginary(), DELTA);
	    }

	    @Test
	    public void divide() {
	        Complex c = new Complex(3, 4).divide(new Complex(5, 6));
	        assertEquals(39.0 / 61, c.getReal(), DELTA);
	        assertEquals(2.0 / 61, c.getImaginary(), DELTA);
	    }

	    @Test
	    public void add() {
	        Complex c = new Complex(3, 4).add(new Complex(5, 6));
	        assertEquals(8, c.getReal(), DELTA);
	        assertEquals(10, c.getImaginary(), DELTA);
	    }

	    @Test
	    public void sub() {
	        Complex c = new Complex(3, 4).sub(new Complex(5, 6));
	        assertEquals(-2, c.getReal(), DELTA);
	        assertEquals(-2, c.getImaginary(), DELTA);
	    }

	    @Test
	    public void negate() {
	        Complex c = new Complex(3, 4).negate();
	        assertEquals(-3, c.getReal(), DELTA);
	        assertEquals(-4, c.getImaginary(), DELTA);
	    }

	    @Test
	    public void power() {
	        Complex c = new Complex(3, 4).power(6);
	        assertEquals(11753, c.getReal(), DELTA);
	        assertEquals(-10296, c.getImaginary(), DELTA);
	    }

	    @Test
	    public void getReImMagAng() {
	        Complex c = new Complex(3, 4);
	        assertEquals(3, c.getReal(), DELTA);
	        assertEquals(4, c.getImaginary(), DELTA);
	        assertEquals(5, c.getMagnitude(), DELTA);
	        assertEquals(0.927295218, c.getAngle(), DELTA);
	    }

	    @Test
	    public void root() {
	        Complex[] roots = new Complex(3, 4).root(4);

	        assertEquals(1.455346690225355, roots[0].getReal(), DELTA);
	        assertEquals(0.34356074972251244, roots[0].getImaginary(), DELTA);
	        assertEquals(-0.34356074972251244, roots[1].getReal(), DELTA);
	        assertEquals(1.455346690225355, roots[1].getImaginary(), DELTA);
	        assertEquals(-1.455346690225355, roots[2].getReal(), DELTA);
	        assertEquals(-0.34356074972251244, roots[2].getImaginary(), DELTA);
	        assertEquals(0.34356074972251244, roots[3].getReal(), DELTA);
	        assertEquals(-1.455346690225355, roots[3].getImaginary(), DELTA);
	    }
	    @Test
		public void ParseTest1() {
			Complex c1 = Complex.parse("1");
			assertEquals(new Complex(1, 0), c1);

			Complex c2 = Complex.parse("-1+0i");
			assertEquals(new Complex(-1, 0), c2);
//
//			Complex c3 = Complex.parse("i");
//			assertEquals(new Complex(0, 1), c3);
//
//			Complex c4 = Complex.parse("0 - 1i");
//			assertEquals(new Complex(0, -1), c4);
		}

		@Test
		public void ParseTest2() {
			Complex c1 = Complex.parse("3.33 -i");
			assertEquals(new Complex(3.33, -1), c1);

			Complex c2 = Complex.parse("-1.2 + 0.5i");
			assertEquals(new Complex(-1.2, 0.5), c2);

			Complex c3 = Complex.parse("-2.22i");
			assertEquals(new Complex(0, -2.22), c3);

			Complex c4 = Complex.parse("2 - 1.44i");
			assertEquals(new Complex(2, -1.44), c4);
		}

		@Test
		public void ParseTest3() {
			Complex c1 = Complex.parse("0");
			assertEquals(new Complex(0, 0), c1);

			Complex c2 = Complex.parse("0i");
			assertEquals(new Complex(0, 0), c2);

			Complex c3 = Complex.parse("0+0i");
			assertEquals(new Complex(0, 0), c3);

			Complex c4 = Complex.parse("0-0i");
			assertEquals(new Complex(0, 0), c4);
		}

		@Test
		public void ParseTest4() {
			Complex c1 = Complex.parse("1");
			assertEquals(new Complex(1, 0), c1);

			Complex c2 = Complex.parse("-1");
			assertEquals(new Complex(-1, 0), c2);

			Complex c3 = Complex.parse("i");
			assertEquals(new Complex(0, 1), c3);

			Complex c4 = Complex.parse("-i");
			assertEquals(new Complex(0, -1), c4);
		}
}
