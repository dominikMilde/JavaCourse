package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {
	@Test
	public void GivenExamplesTest() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM,
				Complex.IM_NEG);
		String crpString = "(2.00+i0.00)*(z-(1.00+i0.00))*(z-(-1.00+i0.00))*(z-(0.00+i1.00))*(z-(0.00-i1.00))";
		assertEquals(crpString, crp.toString());
	}

	@Test
	public void ConversionToComplexPolynomialTest() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM,
				Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		String cpString = "(2.00+i0.00)*z^4+(0.00+i0.00)*z^3+(0.00+i0.00)*z^2+(0.00+i0.00)*z^1+(-2.00+i0.00)";
		assertEquals(cpString, cp.toString());
	}
	
	@Test
	public void IndexTest() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(1, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM,
				Complex.IM_NEG);
		int index = crp.indexOfClosestRootFor(new Complex(-1.8776525177999122, 0.8885807047171461), 0.001);
		//String cpString = "(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)";
		assertEquals(1, index);
	}
	@Test
    public void apply() {
        Complex c = new ComplexRootedPolynomial(new Complex(5, 2), new Complex(1, 0)).apply(new Complex(3, 3));

        assertEquals(new Complex(4, 19), c);
    }


    @Test
    public void indexOfClosestRootFor() {
        int closestRoot = new ComplexRootedPolynomial(new Complex(5, 2), new Complex(3, 4), new
                Complex(1, 1)).indexOfClosestRootFor(new Complex(3.0000001, 4), 1E-3);

        assertEquals(0, closestRoot);
    }

    @Test
    public void indexOfClosestRootForNoClosest() {
        int closestRoot = new ComplexRootedPolynomial(new Complex(5, 2), new Complex(3, 4), new
                Complex(1, 1)).indexOfClosestRootFor(new Complex(20.0000001, 4), 1E-3);

        assertEquals(-1, closestRoot);
    }
}
