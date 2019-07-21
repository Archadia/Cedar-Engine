package uk.ashigaru.engine.gfx.model.obj;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;

import uk.ashigaru.engine.gfx.model.Texture;
import uk.ashigaru.engine.misc.Resource;

public class Material {

	private String name;
	private Vector3f ambientColor;
	private Vector3f diffuseColor;
	private Vector3f specularColor;
	private float specularIntensity;
	private float alpha;
	private Texture diffuseMap;
	
	public Material(String name, Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float specularIntensity, float alpha, Texture diffuseMap) {
		this.name = name;
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseColor;
		this.specularColor = specularColor;
		this.specularIntensity = specularIntensity;
		this.alpha = alpha;
		this.diffuseMap = diffuseMap;
	}
	
	public static Map<String, Material> load(Resource src) {
		String material = src.source();
		String[] lines = material.split("\n");

		Map<String, Material> list = new HashMap<String, Material>();

		boolean first = true;
		String name = "untitled";
		Vector3f ambient = new Vector3f(0,0,0);
		Vector3f diffuse = new Vector3f(0,0,0);
		Vector3f specular = new Vector3f(0,0,0);
		Texture diffuseMap = null;
		float transparency = 1;
		float shininess = 0;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String[] split = line.split(" ");
			if (line.startsWith("newmtl ")) {
				if (!first) {
					list.put(name, new Material(name, ambient, diffuse, specular, transparency, shininess, diffuseMap));
				}
				first = false;
				name = split[1];
			}
			if (line.startsWith("Ns ")) {
				shininess = Float.parseFloat(split[1]);
			}
			if (line.startsWith("Ka ")) {
				ambient = new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
			}
			if (line.startsWith("Kd ")) {
				diffuse = new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
			}
			if (line.startsWith("Ks ")) {
				specular = new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
			}
			if (line.startsWith("d")) {
				transparency = Float.parseFloat(split[1]);
			}
			if(line.startsWith("map_Kd ")) {
				diffuseMap = new Texture(Resource.find(line.split(" ", 2)[1]));
			}
			if (i == lines.length - 1) {
				list.put(name, new Material(name, ambient, diffuse, specular, transparency, shininess, diffuseMap));
			}
		}
		return list;
	}

	public Vector3f getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Vector3f ambientColor) {
		this.ambientColor = ambientColor;
	}

	public Vector3f getDiffuseColor() {
		return diffuseColor;
	}

	public void setDiffuseColor(Vector3f diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	public Vector3f getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Vector3f specularColor) {
		this.specularColor = specularColor;
	}

	public float getSpecularIntensity() {
		return specularIntensity;
	}

	public void setSpecularIntensity(float specularIntensity) {
		this.specularIntensity = specularIntensity;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public String getName() {
		return name;
	}
	
	public Texture getDiffuseMap() {
		return diffuseMap;
	}
	
	public void setDiffuseMap(Texture diffuseMap) {
		this.diffuseMap = diffuseMap;
	}
	
	public String toString() {
		return "Material(" + name + ")";
	}
}
