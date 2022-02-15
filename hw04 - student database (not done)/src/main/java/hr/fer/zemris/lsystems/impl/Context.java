package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * @author Dominik
 * Stores previous states of turtle
 */
public class Context {
	/**
	 * internal stack storge
	 */
	ObjectStack stack = new ObjectStack();
	
	/** Returns state on top of stack
	 * @return Turtle state on top of stack
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}
	/**
	 * Pushes state to top of stack
	 * @param state TurtleState to push
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	/**
	 * removes top of stack
	 */
	public void popState() {
		stack.pop();
	}
}
