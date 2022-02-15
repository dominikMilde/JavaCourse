package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class CalcLayout implements LayoutManager2{
	private int distance;
	private final int ROWS = 5;
	private final int COLUMNS = 7;
	
	private Map<RCPosition, Component> layoutMap = new HashMap<>();
	
	public CalcLayout(int distance) {
		this.distance = distance;
	}
	public CalcLayout() {
		this(0);
	}
	
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	public void removeLayoutComponent(Component comp) {
		layoutMap.remove(comp);
	}

	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(10, 10);
	}

	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(10, 10);
	}

	public void layoutContainer(Container parent) {
		Dimension dim = parent.getSize();
		int widthFull = dim.width;
		int heightFull = dim.height;
		
		int widthElement = widthFull / COLUMNS;
		int heightElement = heightFull / ROWS;
		
		for(Entry<RCPosition, Component> entry : layoutMap.entrySet()) {
			if(entry.getKey().getRow() == 1 && entry.getKey().getColumn() == 1) {
				entry.getValue().setBounds((entry.getKey().getColumn()-1) * widthFull / COLUMNS, (entry.getKey().getRow() -1) * heightFull / ROWS, (widthElement*5 - distance) , heightElement - distance);
				continue;
			}
			entry.getValue().setBounds((entry.getKey().getColumn()-1) * widthFull / COLUMNS, (entry.getKey().getRow() -1) * heightFull / ROWS, widthElement - distance, heightElement - distance);
		}
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) throw new NullPointerException("Passed values cannot be null.");
		
		RCPosition position = null;
		if(constraints instanceof RCPosition) position = (RCPosition) constraints;
		else if(constraints instanceof String) position = RCPosition.parse((String)constraints);
		else {
			throw new IllegalArgumentException();
		}
		
		isValidPosition(position);
		
		if(layoutMap.get(position) != null) {
			throw new CalcLayoutException("Can't add component to given position, already occupied.");
		}
		
		layoutMap.put(position, comp);
	}

	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(8, 8);
	}

	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	public void invalidateLayout(Container target) {
		
	}
	
	private void isValidPosition(RCPosition position) {
		int row = position.getRow();
		int col = position.getColumn();
		
		if(row < 1 || row > ROWS || col < 1 || col > COLUMNS ) {
			throw new CalcLayoutException("Invalid position!");
		}
		else if(row == 1 && col > 1 && col < 6) {
			throw new CalcLayoutException("Invalid position!");
		}
	}
}
