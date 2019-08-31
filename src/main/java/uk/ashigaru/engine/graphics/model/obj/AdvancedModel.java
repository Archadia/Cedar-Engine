 package uk.ashigaru.engine.graphics.model.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector3f;

import uk.ashigaru.engine.graphics.model.RawModel;
import uk.ashigaru.engine.graphics.model.VAO;
import uk.ashigaru.engine.graphics.model.obj.mesh.Mesh;
import uk.ashigaru.engine.misc.Resource;

public class AdvancedModel {
	
	private List<Mesh> modelGroups = new ArrayList<Mesh>();
	
	public static final int MESH_TYPE_VERTICES = 0;
	public static final int MESH_TYPE_NORMALS = 1;
	public static final int MESH_TYPE_TEXTURE_COORDINATES = 2;
	
	public AdvancedModel(List<Mesh> modelParts) {
		this.modelGroups = modelParts;
	}
	
	public List<Mesh> getModelGroups() {
		return modelGroups;
	}

	public AdvancedModel setModelGroups(List<Mesh> modelParts) {
		this.modelGroups = modelParts;
		return this;
	}
	
	public static AdvancedModel load(Resource model, Resource materials) {
		String modelSrc = model.readString();
		String[] lines = modelSrc.split("\n");
		List<Mesh> modelPartList = new ArrayList<Mesh>();
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Vector2f> texes = new ArrayList<Vector2f>();
		Map<String, Material> materialMap = new HashMap<String, Material>();
		Map<String, List<FaceIndex>> faceMap = new HashMap<String, List<FaceIndex>>();

		List<FaceIndex> currentFaceList = new ArrayList<FaceIndex>();
		String currentMaterial = null;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String[] lineSplit = line.split(" ");
			if (line.startsWith("mtllib ")) {
				materialMap = Material.load(materials);
			} else if (line.startsWith("v ")) {
				vertices.add(new Vector3f(Float.valueOf(lineSplit[1]), Float.valueOf(lineSplit[2]), Float.valueOf(lineSplit[3])));
			} else if (line.startsWith("vt ")) {
				texes.add(new Vector2f(Float.valueOf(lineSplit[1]), Float.valueOf(lineSplit[2])));
			} else if (line.startsWith("vn ")) {
				normals.add(new Vector3f(Float.valueOf(lineSplit[1]), Float.valueOf(lineSplit[2]), Float.valueOf(lineSplit[3])));
			} else if (line.startsWith("usemtl ")) {
				if (currentMaterial != null) {
					faceMap.put(currentMaterial, currentFaceList);
					currentFaceList = new ArrayList<FaceIndex>();
				}
				currentMaterial = line.substring(7);
			} else if (line.startsWith("f ")) {
				currentFaceList.add(new FaceIndex(new Vector3f(Float.valueOf(lineSplit[1].split("/")[0]), Float.valueOf(lineSplit[2].split("/")[0]), Float.valueOf(lineSplit[3].split("/")[0])), new Vector3f(Float.valueOf(lineSplit[1].split("/")[2]), Float.valueOf(lineSplit[2].split("/")[2]), Float.valueOf(lineSplit[3].split("/")[2])), new Vector3f(Float.valueOf(lineSplit[1].split("/")[1]), Float.valueOf(lineSplit[2].split("/")[1]), Float.valueOf(lineSplit[3].split("/")[1]))));
			}
		}
		if (!currentFaceList.isEmpty() && currentMaterial != null) {
			faceMap.put(currentMaterial, currentFaceList);
			currentFaceList = new ArrayList<FaceIndex>();
		}
		for (String key : faceMap.keySet()) {
			List<FaceIndex> faceList = faceMap.get(key);
			Material mat = materialMap.get(key);

			float[] verticesArray = new float[faceList.size() * 9];
			float[] normalsArray = new float[faceList.size() * 9];
			float[] textureArray = new float[faceList.size() * 6];
			for (int i = 0; i < faceList.size(); i++) {
				FaceIndex face = faceList.get(i);
				face.vertexIndex.add(new Vector3f(-1, -1, -1));
				face.normalIndex.add(new Vector3f(-1, -1, -1));
				face.texCoordIndex.add(new Vector3f(-1, -1, -1));
				verticesArray[(i * 9) + 0] = vertices.get((int) face.vertexIndex.x).x;
				verticesArray[(i * 9) + 1] = vertices.get((int) face.vertexIndex.x).y;
				verticesArray[(i * 9) + 2] = vertices.get((int) face.vertexIndex.x).z;
				verticesArray[(i * 9) + 3] = vertices.get((int) face.vertexIndex.y).x;
				verticesArray[(i * 9) + 4] = vertices.get((int) face.vertexIndex.y).y;
				verticesArray[(i * 9) + 5] = vertices.get((int) face.vertexIndex.y).z;
				verticesArray[(i * 9) + 6] = vertices.get((int) face.vertexIndex.z).x;
				verticesArray[(i * 9) + 7] = vertices.get((int) face.vertexIndex.z).y;
				verticesArray[(i * 9) + 8] = vertices.get((int) face.vertexIndex.z).z;

				normalsArray[(i * 9) + 0] = normals.get((int) face.normalIndex.x).x;
				normalsArray[(i * 9) + 1] = normals.get((int) face.normalIndex.x).y;
				normalsArray[(i * 9) + 2] = normals.get((int) face.normalIndex.x).z;
				normalsArray[(i * 9) + 3] = normals.get((int) face.normalIndex.y).x;
				normalsArray[(i * 9) + 4] = normals.get((int) face.normalIndex.y).y;
				normalsArray[(i * 9) + 5] = normals.get((int) face.normalIndex.y).z;
				normalsArray[(i * 9) + 6] = normals.get((int) face.normalIndex.z).x;
				normalsArray[(i * 9) + 7] = normals.get((int) face.normalIndex.z).y;
				normalsArray[(i * 9) + 8] = normals.get((int) face.normalIndex.z).z;

				textureArray[(i * 6) + 0] = texes.get((int) face.texCoordIndex.x).x;
				textureArray[(i * 6) + 1] = 1 - texes.get((int) face.texCoordIndex.x).y;
				textureArray[(i * 6) + 2] = texes.get((int) face.texCoordIndex.y).x;
				textureArray[(i * 6) + 3] = 1 - texes.get((int) face.texCoordIndex.y).y;
				textureArray[(i * 6) + 4] = texes.get((int) face.texCoordIndex.z).x;
				textureArray[(i * 6) + 5] = 1 - texes.get((int) face.texCoordIndex.z).y;
			}
			modelPartList.add(new Mesh().push(MESH_TYPE_VERTICES, verticesArray).push(MESH_TYPE_NORMALS, normalsArray).push(MESH_TYPE_TEXTURE_COORDINATES, textureArray).setMaterial(mat));
		}
		return new AdvancedModel(modelPartList);
	}
	
	
	public RawModel convert(RunnableModelConvert func) {
		RawModel model = new RawModel();
		for(Mesh mg : getModelGroups()) {
			VAO vao = new VAO();
			vao.bind();
			func.run(mg, vao);
			vao.unbind();
			model.add(vao, mg.getMaterial());
		}
		return model;
	}
	
	public RawModel convert() {
		return convert((mesh, vao) -> {
			vao.loadf(0, 3, mesh.getArray(MESH_TYPE_VERTICES));
			vao.loadf(1, 3, mesh.getArray(MESH_TYPE_NORMALS));
			vao.loadf(2, 2, mesh.getArray(MESH_TYPE_TEXTURE_COORDINATES));
		});
	}
	
	@FunctionalInterface
	public static interface RunnableModelConvert {
		public void run(Mesh modelGroup, VAO vao);
	}
}
