package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element{
	private double value;
	
	public double getValue() {
		return value;
	}
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
