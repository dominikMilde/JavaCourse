package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel{
	private boolean editable = true;
	private boolean negative = false;
	private String input = "";
	private double value = 0.0;
	private String frozen = null;
	
	private Double activeOperand = null;
	private DoubleBinaryOperator pendingOperation = null;
	
	private List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	private void modelValueChanged() {
		for(CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		this.editable = false;
		this.input = String.valueOf(value);
		this.frozen = this.input;
		modelValueChanged();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		frozen = input;
		editable = true;
		negative = false;
		input = "";
		value = 0.0;
		modelValueChanged();
	}

	@Override
	public void clearAll() {
		this.pendingOperation = null;
		this.activeOperand = null;
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Model is not editable in the moment!");
		frozen = null;
		if(negative) {
			negative = false;
		}
		else {
			negative = true;
		}
		value = -value;
		input = String.valueOf(value);
		modelValueChanged();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Cant edit now");
		frozen = null;
		String tempInput = input + ".";
		try {
			Double.parseDouble(tempInput);
		}
		catch (NumberFormatException e) {
			throw new CalculatorInputException("parsing failed, maybe decimal point already exists");
		}
		input = tempInput;
		//modelValueChanged();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable) throw new CalculatorInputException("Cant edit now");
		frozen = null;
		String tempInput = input;
		double tempValue = value;
		if(input.equals("0") || input.equals("-0")) {
			if(digit != 0) {
				tempInput = tempInput.substring(1);
			}
			else {
				return;
			}
		}
		tempInput = tempInput + digit;
		try {
			tempValue = Double.parseDouble(tempInput);
			if(Double.isFinite(tempValue)) input = tempInput;
			else {
				throw new CalculatorInputException();
			}
		}
		catch (NumberFormatException e) {
			throw new CalculatorInputException("Parsing failed");
		}
		value = tempValue;
		modelValueChanged();
	}

	@Override
	public boolean isActiveOperandSet() {
		return (activeOperand!=null);
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) throw new IllegalStateException("Active operand is not set!");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		
	}
	public void freezeValue(String value) {
		this.frozen = value;
	}
	
	public boolean hasFrozenValue() {
		return frozen != null;
	}
	
	public String toString() {
		if(hasFrozenValue()) {
			return frozen.toString();
		}
		if(Math.abs(value-0.0) < 10E-11) {
			if(this.negative) {
				return "-0";
			}
			else {
				return "0";
			}
			
		}
		if(input.endsWith(".0")) return input.substring(0, input.length() -2);
		return input;
	}
}
