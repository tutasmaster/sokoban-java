package sokoban.game;

import java.io.Serializable;

public class Vector2 implements Serializable {
	public int x, y;
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		Vector2 that = (Vector2) o;
		return x == that.x && y == that.y;
	}
	
	public double getDistanceTo(Vector2 coord) {
		return Math.sqrt(Math.pow(coord.x - x, 2) + Math.pow(coord.y - y, 2));
	}
}
