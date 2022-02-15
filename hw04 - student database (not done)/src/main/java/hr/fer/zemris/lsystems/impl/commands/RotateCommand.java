package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * @author Dominik
 *Changes rotation - changes TurtleState on top of context
 */
public class RotateCommand implements Command{
	
	/**
	 * angle to rotate
	 */
	private double angle;
	
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		ts.setRotation(ts.getRotation().rotated(angle*Math.PI/180.0));
	}

}
