package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CalcModel logic = new CalcModelImpl();
	private Stack<Double> stack = new Stack<>();
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(700,500);
		//pack();
	}
	private void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(10));
		JLabel display = new JLabel(logic.toString(), SwingConstants.RIGHT);
		display.setFont(new Font("Arial", Font.BOLD, 32));
		display.setOpaque(true);
		display.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1, false));
		display.setBackground(Color.YELLOW);
		cp.add(display, new RCPosition(1,1));
		
		logic.addCalcValueListener((x) -> display.setText(logic.toString()));
		
		cp.add(equal, new RCPosition(1,6));
		cp.add(new FunctionalButton("clr", (x) -> logic.clear()), new RCPosition(1,7));
		
		cp.add(inverse, new RCPosition(2,1));
		cp.add(sin, new RCPosition(2,2));
		cp.add(addDigit("7"), new RCPosition(2,3));
		cp.add(addDigit("8"), new RCPosition(2,4));
		cp.add(addDigit("9"), new RCPosition(2,5));
		cp.add(addBinary("/"), new RCPosition(2,6));
		cp.add(new FunctionalButton("res", (x) -> logic.clearAll()), new RCPosition(2,7));
		cp.add(log, new RCPosition(3,1));
		cp.add(cos, new RCPosition(3,2));
		cp.add(addDigit("4"), new RCPosition(3,3));
		cp.add(addDigit("5"), new RCPosition(3,4));
		cp.add(addDigit("6"), new RCPosition(3,5));
		cp.add(addBinary("*"), new RCPosition(3,6));
		cp.add(new FunctionalButton("push", (x) -> stack.push(logic.getValue())), new RCPosition(3,7));
		cp.add(ln, new RCPosition(4,1));
		cp.add(tan, new RCPosition(4,2));
		cp.add(addDigit("1"), new RCPosition(4,3));
		cp.add(addDigit("2"), new RCPosition(4,4));
		cp.add(addDigit("3"), new RCPosition(4,5));
		cp.add(addBinary("-"), new RCPosition(4,6));
		cp.add(new FunctionalButton("pop", (x) -> logic.setValue(stack.pop())), new RCPosition(4,7));
		cp.add(addBinary("x^n"), new RCPosition(5,1));
		cp.add(ctg, new RCPosition(5,2));
		cp.add(addDigit("0"), new RCPosition(5,3));
		cp.add(new FunctionalButton("+/-", (x) -> logic.swapSign()), new RCPosition(5,4));
		cp.add(new FunctionalButton(".", (x) -> logic.insertDecimalPoint()), new RCPosition(5,5));
		cp.add(addBinary("+"), new RCPosition(5,6));
		cp.add(inv, new RCPosition(5,7));
	}

	private FunctionalButton addDigit(String digit) {
		int digitInt = Integer.parseInt(digit);
		ActionListener al = (x) -> logic.insertDigit(digitInt);
		return new FunctionalButton(digit, al);
	}
	private FunctionalButton addBinary(String operation) {
		ActionListener al = null;
		al = r -> prepareBinaryAction(operation);

		return new FunctionalButton(operation, al);	
	}
	
	private void prepareBinaryAction(String operation) {
		if(logic.getPendingBinaryOperation() == null) {
			logic.setActiveOperand(logic.getValue());
			logic.clear();
		}
		else { //first we have to calculate last operation
			logic.setValue(logic.getPendingBinaryOperation().applyAsDouble(logic.getActiveOperand(), logic.getValue()));
			logic.setActiveOperand(logic.getValue());
			logic.clear();
		}
		switch (operation) {
		case "+":
			logic.setPendingBinaryOperation((f,s) -> f+s);
			break;
		case "-":
			logic.setPendingBinaryOperation((f,s) -> f-s);
			break;
		case "*":
			logic.setPendingBinaryOperation((f,s) -> f*s);
			break;
		case "/":
			logic.setPendingBinaryOperation((f,s) -> f/s);
			break;
		case "x^n":
			logic.setPendingBinaryOperation((f,s) -> Math.pow(f, s));
		default:
			break;
		}
	}
	
	private FunctionalButton equal = new FunctionalButton("=", (x) -> prepareEquals());
	private FunctionalButton sin = new FunctionalButton("sin", (x) -> logic.setValue(Math.sin(logic.getValue())));
	private FunctionalButton cos = new FunctionalButton("cos", (x) -> logic.setValue(Math.cos(logic.getValue())));
	private FunctionalButton log = new FunctionalButton("log", (x) -> logic.setValue(Math.log10(logic.getValue())));
	private FunctionalButton ln = new FunctionalButton("ln", (x) -> logic.setValue(Math.log(logic.getValue())));
	private FunctionalButton tan = new FunctionalButton("tan", (x) -> logic.setValue(Math.tan(logic.getValue())));
	private FunctionalButton inverse = new FunctionalButton("1/x", (x) -> logic.setValue(1.0/logic.getValue()));
	private FunctionalButton ctg = new FunctionalButton("ctg", (x) -> logic.setValue(1.0/ Math.tan(logic.getValue())));
	private JCheckBox inv = new JCheckBox("inv");
	//TODO za postavljeni inv zamijeni first i second action;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
	private void prepareEquals() {
		if(logic.isActiveOperandSet() && logic.getPendingBinaryOperation() != null) {
			logic.setValue(logic.getPendingBinaryOperation().applyAsDouble(logic.getActiveOperand(), logic.getValue()));
			logic.clear();
			logic.setPendingBinaryOperation(null);
		}
	}
}
