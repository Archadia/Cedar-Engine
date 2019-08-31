package uk.ashigaru.engine.graphics.model;

import java.util.ArrayList;
import java.util.List;

import uk.ashigaru.engine.graphics.model.obj.Material;

public class RawModel {

	private List<VAOM> renderCouplets = new ArrayList<VAOM>();

	public VAO getVAO(int id) {
		return renderCouplets.get(id).vao;
	}
	
	public Material getMaterial(int id) {
		return renderCouplets.get(id).material;
	}
	
	public void add(VAO vao, Material material) {
		renderCouplets.add(new VAOM(vao, material));
	}
	
	public int size() {
		return renderCouplets.size();
	}
	
	private class VAOM {
		public VAO vao;
		public Material material;
		public VAOM(VAO vao, Material material) {
			this.vao = vao;
			this.material = material;
		}
	}
}
