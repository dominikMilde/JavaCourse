package hr.fer.zemris.math;
import java.lang.Math;

public class ComplexRootedPolynomial {
	private Complex constant;
	private Complex[] roots;
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = new Complex[roots.length];
		for(int i=0; i<roots.length; i++) {
			this.roots[i] = roots[i];
		}
	}
	
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for(Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result.multiply(constant);
	}
	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial[] list = new ComplexPolynomial[roots.length];
		for(int i=0; i<this.roots.length; i++) {
			list[i] = new ComplexPolynomial(roots[i].negate(), Complex.ONE);
		}
		ComplexPolynomial out = new ComplexPolynomial(constant);
		for(int i=0; i<this.roots.length; i++) {
			out = out.multiply(list[i]);
		}
		return out;
	}
	@Override
	public String toString() {
		String out = constant.toString();
		for(Complex root : roots) {
			out += ("*(z-" + root.toString() + ")");
		}
		return out;
	}

	public int indexOfClosestRootFor(Complex z, double treshold) {
		int indexOut = -1;
		for(int i=0; i<roots.length; i++) {
			double distance = Math.sqrt(Math.pow((roots[i].getReal() - z.getReal()), 2) + Math.pow((roots[i].getImaginary() - z.getImaginary()), 2));
			if(distance < treshold) {
				indexOut = i;
				treshold = distance;
			}
		}
		return indexOut;
	}

	public Complex getConstant() {
		return constant;
	}

	public Complex[] getRoots() {
		return roots;
	}
	
}
