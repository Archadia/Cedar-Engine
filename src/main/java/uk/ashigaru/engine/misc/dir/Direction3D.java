package uk.ashigaru.engine.misc.dir;

public enum Direction3D {

	UP(0,1,0),
	DOWN(0,-1,0),
	NORTH(1,0,0),
	EAST(0,0,1),
	SOUTH(-1,0,0),
	WEST(0,0,-1);
	
	public int xOffset, yOffset, zOffset;
	
	Direction3D(int x, int y, int z) {
		this.xOffset = x;
		this.yOffset = y;
		this.zOffset = z;
	}
	
	public Direction3D opposite() {
		if(this == UP) return DOWN;
		if(this == DOWN) return UP;
		if(this == NORTH) return SOUTH;
		if(this == SOUTH) return NORTH;
		if(this == EAST) return WEST;
		if(this == WEST) return EAST;
		return null;
	}
}
