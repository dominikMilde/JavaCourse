package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * @author Dominik
 * As draw but doesn't draw line, just stores new coordinates to context
 */
public class SkipCommand implements Command{
	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		Vector2D toAdd = ts.getRotation().scaled(step*ts.getEfMove());
	
		ts.getPosition().add(toAdd);
	}

}
