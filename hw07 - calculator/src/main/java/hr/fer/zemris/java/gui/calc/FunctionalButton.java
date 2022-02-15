package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class FunctionalButton extends JButton{
	private String text;
	private ActionListener primaryOperation;
	private ActionListener secondaryOperation;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8421024034362058533L;
	public FunctionalButton(String text, ActionListener first, ActionListener second) {
		this.text = text;
		this.primaryOperation = first;
		this.secondaryOperation = second;
		
		initButton();
	}
	public FunctionalButton(String text, ActionListener first) {
		this(text, first, first);
	}
	private void initButton() {
		this.setText(text);
		this.addActionListener(primaryOperation);
		this.setBackground(new Color(200,200,250));
		this.setFont(this.getFont().deriveFont(20f));
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
	}
	public ActionListener getPrimaryOperation() {
		return primaryOperation;
	}
	public void setPrimaryOperation(ActionListener primaryOperation) {
		this.primaryOperation = primaryOperation;
	}
	public ActionListener getSecondaryOperation() {
		return secondaryOperation;
	}
	public void setSecondaryOperation(ActionListener secondaryOperation) {
		this.secondaryOperation = secondaryOperation;
	}
	
}
