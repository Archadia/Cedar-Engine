package uk.ashigaru.engine.font;

public enum FontStyle {

	THIN(0.4f, 0.2f),
	BOLD(0.5f, 0.2f);

	public float width;
	public float edge;
	
	FontStyle(float width, float edge) {
		this.width = width;
		this.edge = edge;
	}
}
