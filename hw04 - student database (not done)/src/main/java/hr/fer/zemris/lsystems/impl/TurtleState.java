package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

public class TurtleState {
	private Vector2D position;
	private Vector2D rotation;
	private Color color;
	private double efMove;
	
	public TurtleState(Vector2D position, Vector2D rotation, Color color, double efMove) {
		this.position = position;
		this.rotation = rotation;
		this.color = color;
		this.efMove = efMove;
	}
	public TurtleState copy() {
		return new TurtleState(this.position, this.rotation, this.color, this.efMove);
	}
	public Vector2D getRotation() {
		return rotation;
	}
	public void setRotation(Vector2D rotation) {
		this.rotation = rotation;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public double getEfMove() {
		return efMove;
	}
	public void setEfMove(double efMove) {
		this.efMove = efMove;
	}
	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
}
