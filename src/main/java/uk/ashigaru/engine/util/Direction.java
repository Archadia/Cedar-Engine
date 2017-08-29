package uk.ashigaru.engine.util;

public enum Direction {

	UP(0,1,0),
	DOWN(0,-1,0),
	NORTH(1,0,0),
	EAST(0,0,1),
	SOUTH(-1,0,0),
	WEST(0,0,-1);
	
	public int xOffset, yOffset, zOffset;
	
	Direction(int x, int y, int z) {
		this.xOffset = x;
		this.yOffset = y;
		this.zOffset = z;
	}
}
