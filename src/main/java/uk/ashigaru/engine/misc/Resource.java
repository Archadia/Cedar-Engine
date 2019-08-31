package uk.ashigaru.engine.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Resource {

	private String filename;
	
	public Resource(String filename) {
		this.filename = filename;
	}
	
	public URL getPath() {
		return getClass().getClassLoader().getResource(filename);
	}

	public String readString() {
		URL url = getClass().getClassLoader().getResource(filename);
		StringBuilder result = new StringBuilder("");
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = "";
			while((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return result.toString();
	}
}