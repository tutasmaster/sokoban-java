package sokoban.game;

import java.io.Serializable;

/**
 * Represents a 2d vector containing an x and y position. <br>
 * It's used as shorthand for <i>int x, int y</i> in a lot of cases,
 * or as a way to get distances during pathfinding calculations.
 */
public class Vector2 implements Serializable {
	/**
	 * Can take any arbitrary number as a position.
	 */
	public int x, y;
	
	/**
	 * Constructs the vector using an x and y position.
	 * @param x position
	 * @param y position
	 */
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns if two vectors have the same x and y values.<br>
	 * It's used when we a simple == comparison is not enough to determine if two
	 * values are the same, such as having different objects.
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		Vector2 that = (Vector2) o;
		return x == that.x && y == that.y;
	}
	
	/**
	 * It gets a distance between two vectors.
	 * For this project it uses a Pythagorean theorem for its calculation.
	 * In a later iteration, given the context of its usage (pathfinding) it should
	 * use a Manhattan distance instead.
 	 * @param coord
	 * @return
	 */
	public double getDistanceTo(Vector2 coord) {
		return Math.sqrt(Math.pow(coord.x - x, 2) + Math.pow(coord.y - y, 2));
	}
}
