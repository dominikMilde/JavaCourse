package hr.fer.oprpp1.hw01;
import static java.lang.Math.*;

import java.util.Locale;

/**
 * @author Dominik
 * Implementation of support for working with complex numbers. Represents an unmodifiable complex number.
 */
/**
 * @author Dominik
 *
 */
/**
 * @author Dominik
 *
 */
public class ComplexNumber {
	/**
	 * Real part of complex number
	 */
	private final double real;
	/**
	 * Imaginary part of complex number
	 */
	private final double imaginary;
	
	/**
	 * Sets real and imaginary values for complex number
	 * @param real
	 * @param imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	/**
	 * Factory method, creates complex number on real axis
	 * @param real
	 * @return New ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}
	/**
	 * Factory method, creates complex number on imaginary axis
	 * @param imaginary
	 * @return New ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}
	/**
	 * Factory method, creates complex number from given parameters
	 * @param magnitude
	 * @param angle
	 * @return New ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude<0) throw new IllegalArgumentException("magnitude must be positive value");
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	/**
	 * Factory method, creates complex number from parsing text
	 * @param text Complex number in form "a+bi"
	 * @return new ComplexNumber(a, b)
	 * @throws IllegalArgumentException if parsing wasn't successful
	 */
	public static ComplexNumber parse(String text) {
		text = text.replaceAll("\\s+","");
		double real = 0, imaginary = 0;
		String leftPart, rightPart;
		boolean leftNeg = false;
		boolean rightNeg = false;
		try {
			if (text.indexOf("-") == 0) {
				leftNeg = true;
			}
			if (text.substring(1).contains("-")) {
				rightNeg = true;
			}
			
			String[] cutLeftRight = text.split("[-+]");
			if(cutLeftRight[0].isEmpty()) {
				//System.out.println("OPAZIO!!!");
				if(cutLeftRight.length == 3) {
					leftPart = cutLeftRight[1];
					rightPart = cutLeftRight[2];
				}
				else {
					leftPart = cutLeftRight[1];
					rightPart = null;
				}
			}
			else {
				if(cutLeftRight.length == 2) {
					leftPart = cutLeftRight[0];
					rightPart = cutLeftRight[1];
				}
				else {
					leftPart = cutLeftRight[0];
					rightPart = null; //doesn't exist
				}
			}
			//System.out.println("L: " + leftPart);
			//System.out.println("R: " + rightPart);
			
			if (leftPart.indexOf('i') != -1) { //i is in the left part
				if(leftPart.length() > 1) {
					//System.out.println(leftPart);
					imaginary = Double.parseDouble(leftPart.substring(0, leftPart.length() - 1));
					if(leftNeg) imaginary *= -1.0;
				}
				else {
					imaginary = 1.0;
					if(leftNeg) imaginary *= -1.0;
				}
			}
			else {
			    real = Double.parseDouble(leftPart);
			    if(leftNeg) real *=-1.0;
			}
			
			if (rightPart != null) {
			    if (rightPart.indexOf('i') != -1) { //i is in right part
			    	if(rightPart.length() > 1) {
			    		imaginary = Double.parseDouble(rightPart.substring(0,rightPart.length() - 1));
			    		if(rightNeg) imaginary *=-1.0;
			    	}
					else {
						imaginary = 1.0;
						if(rightNeg) imaginary *=-1.0;
					}
			    }
			    else {
			    	real = Double.parseDouble(rightPart);
			    	if (rightNeg) real *= -1.0;
			    }
			}
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Parsing is impossible, error in expression.");
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Getter method
	 * @return Real component of complex number
	 */
	public double getReal() {
		return this.real;
	}
	/**
	 * Getter method
	 * @return Imaginary component of complex number
	 */
	public double getImaginary() {
		return this.imaginary;
	}
	/** Calculates and returns magnitude of complex number
	 * @return magnitude
	 */
	public double getMagnitude() {
		return sqrt(real*real + imaginary*imaginary);
	}
	/**
	 * Calculates and returns angle of complex number
	 * @return angle [0, 2*PI]
	 */
	public double getAngle() {
		double angle = atan2(imaginary, real);
		if(angle < 0) return (angle + 2*PI);
		return angle;
	}
	
	/**
	 * Adds complex number c to this complex number and returns new complex number with added value.
	 * @param c ComplexNumber to be added
	 * @return new ComplexNumber 
	 * @throws NullPointerException if null is passed
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) throw new NullPointerException("Can't pass null value!");
		return new ComplexNumber(this.real+c.real, this.imaginary + c.imaginary);
	}
	/**
	 * Subtracts complex number c from this complex number and returns new complex number with subtracted value.
	 * @param c ComplexNumber to be subtracted from this ComplexNumber
	 * @return new ComplexNumber 
	 * @throws NullPointerException if null is passed
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) throw new NullPointerException("Can't pass null value!");
		return new ComplexNumber(this.real-c.real, this.imaginary - c.imaginary);
	}
	/**
	 * Multiplies complex number c to this complex number and returns new complex number with multiplied  value.
	 * @param c ComplexNumber to be multiplied
	 * @return new ComplexNumber 
	 * @throws NullPointerException if null is passed
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) throw new NullPointerException("Can't pass null value!");
		return new ComplexNumber(this.real*c.real - this.imaginary*c.imaginary, this.real*c.imaginary + this.imaginary*c.real);  //(a+bi)(c+di) = (ac−bd) + (ad+bc)i
	}
	/**
	 * Divides complex number c from this complex number and returns new complex number with divided value.
	 * @param c ComplexNumber to be divided from this ComplexNubmer
	 * @return new ComplexNumber 
	 * @throws NullPointerException if null is passed
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) throw new NullPointerException("Can't pass null value!");
		double divideBy = c.real * c.real + c.imaginary * c.imaginary;
		double realToBe = (this.real*c.real + this.imaginary*c.imaginary) / divideBy;
		double imaginaryToBe = (this.imaginary*c.real - this.real*c.imaginary)/divideBy;
		
		return new ComplexNumber(realToBe, imaginaryToBe);
	}
	
	/**
	 * Raises ComplexNumber to power n and returns new ComplexNumber
	 * @param n exponent
	 * @return new ComplexNumber with powered value
	 * @throws IllegalArgumentException if n<0
	 */
	public ComplexNumber power(int n) {
		if(n<0) throw new IllegalArgumentException("n must be zero or greater, it was: " + n);
		
		double r = getMagnitude();
		double phi = getAngle();
		double realToBe = pow(r, n) * cos(n*phi);
		double imaginaryToBe = pow(r, n) * sin(n*phi);
		
		return new ComplexNumber(realToBe, imaginaryToBe);
	}
	
	/** Calculates roots of ComplexNumber and returns array of ComplexNumber objects
	 * @return array of ComplexNumber objects
	 * @throws IllegalArgumentException if n<=0
	 */
	public ComplexNumber[] root(int n) {
		if(n<=0) throw new IllegalArgumentException("n must be greater than zero, it was: " + n);
		ComplexNumber[] list = new ComplexNumber[n];
		double r = getMagnitude();
		double phi = getAngle();
		double realToBe, imaginaryToBe;
		
		for(int i=0; i<n; i++) {
			realToBe = pow(r, 1d/n) * cos((phi + 2*PI*i)/n);
			imaginaryToBe = pow(r, 1d/n) * sin((phi + 2*PI*i)/n);
			
			list[i] = new ComplexNumber(realToBe, imaginaryToBe);
		}
		return list;
	}
	
	/**
	 *Returns string of this ComplexNumber
	 */
	public String toString() {
		String s = "(" + String.format(Locale.US, "%.2f", real);
		if(imaginary > 0.0) s+= "+";
		s += "i" + String.format(Locale.US, "%.2f", imaginary) + ")";
		return s;
	}
}
