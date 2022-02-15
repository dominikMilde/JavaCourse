package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * @author Dominik
 *Changes effective move - changes TurtleState on top of context
 */
public class ScaleCommand implements Command{
	/**
	 * factor to scale
	 */
	private double factor;
	
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		ts.setEfMove(ts.getEfMove() * factor);
	}

}
