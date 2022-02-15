package hr.fer.oprpp1.custom.scripting.elems;

public class ElementFunction extends Element{
	private String name;
	public String getName() {
		return name;
	}
	@Override
	public String asText() {
		return name;
	}
}
