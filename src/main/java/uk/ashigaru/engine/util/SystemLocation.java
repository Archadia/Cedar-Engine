package uk.ashigaru.engine.util;

public enum SystemLocation {

	RESOURCE(System.getProperty("user.dir") + "/src/main/res/"),
	LOGS(System.getProperty("user.dir") + "/logs/");
	
	private String path;
	
	SystemLocation(String path) {
		this.path = path;
	}
	
	public String path() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}