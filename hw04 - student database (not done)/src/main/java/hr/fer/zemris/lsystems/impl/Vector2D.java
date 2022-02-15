package hr.fer.zemris.lsystems.impl;

/**
 * @author Dominik
 * Represents 2d vector, double coordinates
 */
public class Vector2D {
	/**
	 * x component
	 */
	private double x;
	/**
	 * y component
	 */
	private double y;
	
	/**
	 * Contructor with 2 values
	 * @param x X comp
	 * @param y Y comp
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Getter method
	 * @return x component
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * Getter method
	 * @return y component
	 */
	public double getY() {
		return this.y;
	}
	/**
	 * Changes this {@link Vector2D}, adds offset
	 * @param offset Vector to add to current
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	/**
	 * Returns new {@link Vector2D}, added current and offset
	 * @param offset Vector to add to current
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}
	/**
	 * Changes this {@link Vector2D}, rotates for angle in radians
	 * @param angle (radians)
	 */
	public void rotate(double angle) {
		double xOld = x, yOld = y;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		x = xOld * cos - yOld * sin;
		y = xOld * sin + yOld * cos;
	}
	/**
	 * Returns new {@link Vector2D}, rotated current by angle
	 * @param angle in radians
	 */
	public Vector2D rotated(double angle) {
		double xOld = x, yOld = y;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		return new Vector2D(xOld * cos - yOld * sin, xOld * sin + yOld * cos);
	}
	/**
	 * Changes this {@link Vector2D}, scales, multiplies x and y by scaler
	 * @param scaler value to scale by
	 */
	public void scale(double scaler) {
		x = x*scaler;
		y = y*scaler;
	}
	/**
	 * Returns new {@link Vector2D}, scaled current
	 * @param scaler number to scale by
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(scaler * x, scaler * y);
	}
	
	/**
	 * @return new instance of {@link Vector2D} with same x and y values
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
