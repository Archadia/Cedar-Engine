package uk.ashigaru.engine.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Resource {

	private String file;
	private boolean raw = false;
	
	public Resource(String file) {
		this.file = file;
		this.raw = false;
	}
	
	public Resource(String file, boolean raw) {
		this(file);
		this.raw = raw;
	}
	
	public Resource(Resource parent, String child) {
		this.file = parent.append(child).file;
	}
	
	public Resource(Resource parent, Resource child) {
		this.file = parent.file + child.file;
	}
	
	public Resource(Resource parent, String child, boolean raw) {
		this.file = parent.append(child).file;
		this.raw = raw;
	}
	
	public Resource(Resource parent, Resource child, boolean raw) {
		this.file = parent.file + child.file;
		this.raw = raw;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getPath() {
		if(raw) return file;
		return SystemLocation.RESOURCE.path() + "" + file;
	}
	
	public File getFile() {
		return new File(getPath());
	}
	
	public static Resource find(String file) {
		return new Resource(file);
	}
	
	public static Resource find(String file, boolean raw) {
		return new Resource(file, raw);
	}
	
	public static Resource find(Resource parent, String child) {
		return new Resource(parent, child);
	}
	
	public static Resource find(Resource parent, Resource child) {
		return new Resource(parent, child);
	}
	
	public static Resource find(Resource parent, String child, boolean raw) {
		return new Resource(parent, child, raw);
	}
	
	public static Resource find(Resource parent, Resource child, boolean raw) {
		return new Resource(parent, child, raw);
	}
	
	public Resource append(String appendage) {
		return new Resource(this.file + appendage);
	}
	
	public String source() {
		if(getFile().isDirectory()) return "";
		StringBuilder src = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.getPath()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				src.append(line + "\n");
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return src.toString();
	}
}