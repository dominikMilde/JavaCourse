package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstatntInteger extends Element{
	private int value;
	public int getValue() {
		return value;
	}
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
