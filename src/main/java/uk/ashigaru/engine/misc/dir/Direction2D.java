package uk.ashigaru.engine.misc.dir;

public enum Direction2D {

	NORTH(0,-1),
	EAST(1, 0),
	SOUTH(0,1),
	WEST(-1,0);
	
	public int xOffset, yOffset;
	
	Direction2D(int x, int y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
	public Direction2D opposite() {
		if(this == NORTH) return SOUTH;
		if(this == SOUTH) return NORTH;
		if(this == EAST) return WEST;
		if(this == WEST) return EAST;
		return null;
	}
	
	public Direction3D convert() {
		switch(this) {
			case NORTH: return Direction3D.NORTH;
			case SOUTH: return Direction3D.SOUTH;
			case WEST: return Direction3D.WEST;
			case EAST: return Direction3D.EAST;
		}
		return null;
	}
}
