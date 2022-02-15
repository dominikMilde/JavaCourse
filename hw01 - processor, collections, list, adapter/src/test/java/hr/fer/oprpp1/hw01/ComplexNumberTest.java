package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

import static java.lang.Math.PI;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void testConstructorAndBasicGetters() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(2, -1);
		
		assertEquals(1, c1.getReal());
		assertEquals(1, c1.getImaginary());
		assertEquals(2, c2.getReal());
		assertEquals(-1, c2.getImaginary());
	}
	
	@Test 
	public void testMagnitudeAndAngleCalculation() {
		ComplexNumber c1 = new ComplexNumber(1, 0);
		ComplexNumber c2 = new ComplexNumber(-1, 0);
		ComplexNumber c3 = new ComplexNumber(0, 1);
		ComplexNumber c4 = new ComplexNumber(0, -1);
		ComplexNumber c5 = new ComplexNumber(-4, -3);
		
		ComplexNumber c11 = new ComplexNumber(1, 1);
		ComplexNumber c12 = new ComplexNumber(-1, 1);
		ComplexNumber c13 = new ComplexNumber(-1, -1);
		ComplexNumber c14 = new ComplexNumber(1, -1);
		
		assertEquals(1, c1.getMagnitude(),0.000001);
		assertEquals(1, c2.getMagnitude(),0.000001);
		assertEquals(1, c3.getMagnitude(),0.000001);
		assertEquals(5, c5.getMagnitude(),0.000001);
		
		assertEquals(0, c1.getAngle(),0.000001);
		assertEquals(PI, c2.getAngle(),0.000001);
		assertEquals(PI/2, c3.getAngle(),0.000001);
		assertEquals(PI*3/2, c4.getAngle(),0.000001);
		
		assertEquals(PI/4, c11.getAngle(),0.000001);
		assertEquals(3*PI/4, c12.getAngle(),0.000001);
		assertEquals(5*PI/4, c13.getAngle(),0.000001);
		assertEquals(7*PI/4, c14.getAngle(),0.000001);
		}
	
	@Test
	public void testFromRealAndImaginary() {
		ComplexNumber c1 = ComplexNumber.fromReal(3);
		ComplexNumber c2 = ComplexNumber.fromImaginary(-1);
		
		assertEquals(3.0, c1.getReal(),0.000001);
		assertEquals(0.0, c1.getImaginary(),0.000001);
		assertEquals(0.0, c2.getReal(),0.000001);
		assertEquals(-1.0, c2.getImaginary(),0.000001);
	}
	
	@Test
	public void testMagnitudeAndAngle() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(4, 0);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(8, 3*PI/2);
		ComplexNumber c3 = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), 3*PI/4);
		
		assertEquals(4, c1.getReal(),0.000001);
		assertEquals(0.0, c1.getImaginary(), 0.000001);
		assertEquals(-8, c2.getImaginary(), 0.000001);
		assertEquals(0.0d, c2.getReal(), 0.000001);
		
		assertEquals(-1, c3.getReal(),0.000001);
		assertEquals(1, c3.getImaginary(), 0.000001);
	}
	
	@Test
	public void testNullValuePassed() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		
		
		Assertions.assertThrows(NullPointerException.class, () -> {c1.add(null);});
		Assertions.assertThrows(NullPointerException.class, () -> {c1.sub(null);});
		Assertions.assertThrows(NullPointerException.class, () -> {c1.mul(null);});
		Assertions.assertThrows(NullPointerException.class, () -> {c1.div(null);});
		
	}
	@Test
	public void testAdd() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(2, -1);
		
		ComplexNumber c3 = c1.add(c2);
		assertEquals(3, c3.getReal(),0.000001);
		assertEquals(0, c3.getImaginary(), 0.000001);
	}
	
	@Test
	public void testSub() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(2, -1);
		
		ComplexNumber c3 = c1.sub(c2);
		assertEquals(-1, c3.getReal(),0.000001);
		assertEquals(2, c3.getImaginary(), 0.000001);
	}
	@Test
	public void testMul() {
		ComplexNumber c1 = new ComplexNumber(2, 5);
		ComplexNumber c2 = new ComplexNumber(4, -3);
		
		ComplexNumber c3 = new ComplexNumber(5, 2);
		ComplexNumber c4 = new ComplexNumber(5, -2);
		
		ComplexNumber c12 = c1.mul(c2);
		assertEquals(23, c12.getReal(),0.000001);
		assertEquals(14, c12.getImaginary(), 0.000001);
		
		ComplexNumber c34 = c3.mul(c4);
		assertEquals(29, c34.getReal(),0.000001);
		assertEquals(0, c34.getImaginary(), 0.000001);
	}
	@Test
	public void testDiv() {
		ComplexNumber c1 = new ComplexNumber(3, -1);
		ComplexNumber c2 = new ComplexNumber(2, -2);
		
		ComplexNumber c3 = new ComplexNumber(-3, 5);
		ComplexNumber c4 = new ComplexNumber(-3, 1);
		
		ComplexNumber c12 = c1.div(c2);
		assertEquals(1, c12.getReal(),0.000001);
		assertEquals(0.5, c12.getImaginary(), 0.000001);
		
		ComplexNumber c34 = c3.div(c4);
		assertEquals(7d/5, c34.getReal(),0.000001);
		assertEquals(-6d/5, c34.getImaginary(), 0.000001);
	}
	
	@Test
	public void testIllegalArgPowerAndRoot() {
		ComplexNumber c1 = new ComplexNumber(2, 5);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {c1.power(-1);});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {c1.root(0);});
	}
	
	@Test
	public void testPower() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber c2 = c1.power(2);
		assertEquals(-4, c2.getReal(),0.000001);
		assertEquals(0, c2.getImaginary(), 0.000001);
		
		ComplexNumber c3 = new ComplexNumber(-4, 8);
		ComplexNumber c4 = c3.power(8);
		assertEquals(-34537472, c4.getReal(),0.000001);
		assertEquals(-22020096, c4.getImaginary(), 0.000001);
		
		ComplexNumber c5 = new ComplexNumber(-4, 8);
		ComplexNumber c6 = c5.power(1);
		assertEquals(-4, c6.getReal(),0.000001);
		assertEquals(8, c6.getImaginary(), 0.000001);
	}
	
	@Test 
	public void testRoot() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber[] list1 = c1.root(2);
		assertEquals(1.674149, list1[0].getReal(), 0.000001);
		assertEquals(0.89597747612, list1[0].getImaginary(), 0.000001);
		assertEquals(-1.674149, list1[1].getReal(), 0.000001);
		assertEquals(-0.89597747612, list1[1].getImaginary(), 0.000001);
		
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber[] list2 = c2.root(1);
		assertEquals(2, list2[0].getReal(), 0.000001);
		assertEquals(3, list2[0].getImaginary(), 0.000001);
		
		ComplexNumber c3 = new ComplexNumber(-8, 1);
		ComplexNumber[] list3 = c3.root(4);
		assertEquals(1.22797501633099, list3[0].getReal(), 0.000001);
		assertEquals(1.1539015875, list3[0].getImaginary(), 0.000001);
		assertEquals(-1.15390158752841, list3[1].getReal(), 0.000001);
		assertEquals(1.227975016330, list3[1].getImaginary(), 0.000001);
		assertEquals(-1.227975016330, list3[2].getReal(), 0.000001);
		assertEquals(-1.1539015875, list3[2].getImaginary(), 0.000001);
		assertEquals(1.15390158752841, list3[3].getReal(), 0.000001);
		assertEquals(-1.2279750163, list3[3].getImaginary(), 0.000001);
	}
	@Test
	public void testToString() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		assertEquals("2.00+3.00i", c1.toString());
		c1 = new ComplexNumber(0, 3);
		assertEquals("+3.00i", c1.toString());
		c1 = new ComplexNumber(-2, 0);
		assertEquals("-2.00", c1.toString());
		c1 = new ComplexNumber(1, -6);
		assertEquals("1.00-6.00i", c1.toString());
		c1 = new ComplexNumber(0, -1);
		assertEquals("-i", c1.toString());
		c1 = new ComplexNumber(-1, 0);
		assertEquals("-1.00", c1.toString());
		c1 = new ComplexNumber(0, -1);
		assertEquals("-i", c1.toString());
		c1 = new ComplexNumber(0 , 0);
		assertEquals("0+0i", c1.toString());
	}
	@Test
	public void testParsing() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {ComplexNumber c1 = ComplexNumber.parse("ii");});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {ComplexNumber c1 = ComplexNumber.parse("3 - j");});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {ComplexNumber c1 = ComplexNumber.parse("");});
		
		ComplexNumber c1 = ComplexNumber.parse("-2");
		ComplexNumber c2 = ComplexNumber.parse("2i");
		ComplexNumber c3 = ComplexNumber.parse("3-i");
		
		ComplexNumber c4 = c1.add(c2);
		assertEquals(-2, c4.getReal(),0.000001);
		assertEquals(2, c4.getImaginary(), 0.000001);
		
		ComplexNumber c5 = c1.sub(c3);
		assertEquals(-5, c5.getReal(),0.000001);
		assertEquals(1, c5.getImaginary(), 0.000001);
	}	
}
