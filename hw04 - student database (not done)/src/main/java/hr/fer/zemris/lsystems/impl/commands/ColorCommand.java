package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * @author Dominik
 * Changes color - changes TurtleState on top of context
 */
public class ColorCommand implements Command{
	
	/**
	 * color to set
	 */
	private Color color;
	/**
	 * @param color to set
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		ts.setColor(this.color);
	}
}
