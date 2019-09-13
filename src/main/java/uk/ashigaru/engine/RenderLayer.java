package uk.ashigaru.engine;

public enum RenderLayer {

	GUI_TEXT(0f),
	GUI_FOREGROUND(-0.1f),
	GUI_BACKGROUND(-0.2f),
	WORLD_FOREGROUND(-0.3f),
	WORLD_BACKGROUND(-0.4f);
	
	public final float z;
	
	RenderLayer(float z) {
		this.z = z;
	}
}
