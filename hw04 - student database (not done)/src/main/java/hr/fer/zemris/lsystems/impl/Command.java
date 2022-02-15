package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * @author Dominik
 * Defines signature of execute command
 */
public interface Command {
	/**
	 * 
	 * @param ctx Context storage
 	 * @param painter Reference to painter which should paint 
	 */
	void execute(Context ctx, Painter painter);
}
