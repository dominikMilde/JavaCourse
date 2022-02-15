package hr.fer.zemris.java.hw06.shell;

public class ShellIOException extends RuntimeException {

	/**
	 * generated UID
	 */
	private static final long serialVersionUID = 3844731367581627444L;
	
	public ShellIOException() {
		this("There was an error during reading/writing.");
	}

	public ShellIOException(String string) {
		super(string);
	}
}
