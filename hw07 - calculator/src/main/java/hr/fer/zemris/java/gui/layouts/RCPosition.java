package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public static RCPosition parse(String input) {
		String[] splitted = input.split(","); 
		if(splitted.length != 2) throw new IllegalArgumentException("Input must be: X,Y");
		return new RCPosition(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
}
