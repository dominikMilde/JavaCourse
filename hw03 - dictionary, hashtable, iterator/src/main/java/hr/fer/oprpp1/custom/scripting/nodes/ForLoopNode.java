package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node{
	private ElementVariable  variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	public ElementVariable getVariable() {
		return variable;
	}
	public Element getStartExpression() {
		return startExpression;
	}
	public Element getEndExpression() {
		return endExpression;
	}
	public Element getStepExpression() {
		return stepExpression;
	}
	
}
