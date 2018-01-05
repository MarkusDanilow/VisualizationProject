package com.base.engine.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class WavefrontModelLoader {

	public static final String DIRECTORY = "res/models/";

	public static Model load(String modelFile, String modelClassName) {
		try {
			List<Vector3f> vertices = new ArrayList<>();
			List<Vector3f> normals = new ArrayList<>();
			boolean isVertex = false, isNormal = false, isFace = false;
			if (modelFile != null && modelFile.length() > 0) {
				Model model = null;
				if (modelClassName == null) {
					model = new Model();

				} else {
					model = (Model) Class.forName("com.base.engine.models." + modelClassName).newInstance();
				}
				File objFile = new File(DIRECTORY + modelFile + ".obj");
				BufferedReader br = new BufferedReader(new FileReader(objFile));
				String line;
				while ((line = br.readLine()) != null) {
					String[] s = line.split("\\s+");
					isVertex = s[0] != null && s[0].equals("v");
					isNormal = s[0] != null && s[0].equals("vn");
					isFace = s[0] != null && s[0].equals("f");
					if (isVertex || isNormal) {
						float vx = Float.parseFloat(s[1]);
						float vy = Float.parseFloat(s[2]);
						float vz = Float.parseFloat(s[3]);
						Vector3f vector = new Vector3f(vx, vy, vz);
						if (isVertex) {
							vertices.add(vector);
						} else if (isNormal) {
							normals.add(vector);
						}
						model.addColor(model.getDefaultColor());
					} else if (isFace) {
						for (int i = 3; i > 0; i--) {
							String[] indices = s[i].split("/");
							int vIndex = Integer.parseInt(indices[0]) - 1;
							model.addVertex(vertices.get(vIndex));
							if (indices.length > 2) {
								int nIndex = Integer.parseInt(indices[2]) - 1;
								model.addNormal(normals.get(nIndex));
							}
							model.addColor(model.getDefaultColor());
						}
					}
				}
				br.close();
				model.setVisible(true);
				return model;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
