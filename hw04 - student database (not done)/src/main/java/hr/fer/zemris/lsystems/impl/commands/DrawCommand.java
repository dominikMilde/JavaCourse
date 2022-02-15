package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * @author Dominik
 * Draws line and sets new coordinates to TurtleState
 */
public class DrawCommand implements Command{
	/**
	 * step to make
	 */
	private double step;

	public DrawCommand(double step) {
		this.step = step;
	}
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		Vector2D old = ts.getPosition();
		Vector2D toAdd = ts.getRotation().scaled(step*ts.getEfMove());
		
		Vector2D newP = old.added(toAdd);
		painter.drawLine(old.getX(),old.getY(), newP.getX(), newP.getY(), ts.getColor(), 1f);
		ts.setPosition(newP);
	}

}
