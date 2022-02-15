package hr.fer.zemris.java.gui.layouts;

public class CalcLayoutException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CalcLayoutException(String string) {
		super(string);
	}
	public CalcLayoutException() {
		this("Something went with calculator layout");
	}

}
