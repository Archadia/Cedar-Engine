package uk.ashigaru.engine.window;

public interface SimpleCallback {

	public interface SimpleKeyCallback extends SimpleCallback {
		public void execute(int key, int action);
	}
	
	public interface SimpleWindowSizeCallback extends SimpleCallback {
		public void execute(int width, int height);
	}
	
	public interface SimpleMouseButtonCallback extends SimpleCallback {
		public void execute(int button, int action);
	}
	
	public interface SimpleMouseScrollCallback extends SimpleCallback {
		public void execute(double xoffset, double yoffset);
	}
	
	public interface SimpleMousePosCallback extends SimpleCallback {
		public void execute(double xpos, double ypos);
	}
	
	public interface SimpleCharCallback extends SimpleCallback {
		public void execute(int codepoint);
	}
}
