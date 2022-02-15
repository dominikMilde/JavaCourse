package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * @author Dominik
 * pushes state from top of context once again
 */
public class PushCommand implements Command{

	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		ctx.pushState(ts.copy());		
	}

}
