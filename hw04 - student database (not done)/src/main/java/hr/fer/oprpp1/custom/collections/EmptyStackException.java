package hr.fer.oprpp1.custom.collections;

/**
 * @author Dominik
 * Custom Exception made to eliminate problem of popping and peeking from empty stack
 */
public class EmptyStackException extends RuntimeException{

	
	private static final long serialVersionUID = 735807516317944609L;
		
	/**
	 * Constructor, delegates text to super(text)
	 * @param text Text to display to user.
	 */
	public EmptyStackException(String text) {
		super(text);
	}
	
	/**
	 * default constructor, calls this("Can't execute on empty stack.")
	 */
	public EmptyStackException() {
		this("Can't execute on empty stack.");
	}
	
}
