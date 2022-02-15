package hr.fer.zemris.math;

public class ComplexPolynomial {
	private Complex[] factors;
	private short order;
	public ComplexPolynomial(Complex ...factors) {
		this.order = (short) (factors.length-1);
		this.factors = new Complex[factors.length];
		for(int i=0; i<factors.length; i++) {
			this.factors[i] = factors[i];
		}
	}
	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	public short order() {
		return this.order;
		
	}
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[this.factors.length + p.factors.length - 1];
		for(int i = 0; i<result.length; i++) {
			result[i] = Complex.ZERO;
		}
		for(int i=0; i<this.factors.length; i++) {
			for(int j=0; j<p.factors.length; j++) {
				result[i+j] = result[i+j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		return new ComplexPolynomial(result);
	}
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[this.factors.length - 1];
		for(int i=1; i<this.factors.length; i++) {
			result[i-1] = this.factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(result);
		
	}
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex applied = new Complex(0,0);
		for(int i=0; i<this.factors.length; i++) {
			applied = applied.add(factors[i].multiply(z.power(i)));
		}
		return applied;
	}
	@Override
	public String toString() {
		String out = "";
		for(int i=this.factors.length-1; i>=0; i--) {
			out += factors[i].toString() + "*z^" + i + "+";
		}
		out = out.substring(0, out.length()-5);
		return out;
	}
	public Complex[] getFactors() {
		return factors;
	}
	public short getOrder() {
		return order;
	}

}
