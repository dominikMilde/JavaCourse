package hr.fer.zemris.java.hw06.shell;

public class Parser {
	public static String exctractCommandName(String reading) {
		String[] splitted = reading.split("\\s+");
		return splitted[0];
	}

	public static String exctractArguments(String reading) {
		String[] splitted = reading.split("\\s+");
		reading = reading.substring(splitted[0].length()).strip();
		return reading;
	}
	
	public static String extractPath(String reading) {
		
		return reading;
	}
}
