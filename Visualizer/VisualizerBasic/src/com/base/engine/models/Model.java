package com.base.engine.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import com.base.common.resources.DataInspector;

public class Model {

	public static final int[] LOD_STAGES = { 1, 2, 4, 8, 16 };
	public static final float LOD_STEP = 100.0f;

	protected int lod;

	protected Map<String, Vector3f> vertices;
	protected List<Vector3f> normals;
	protected List<Integer> indices;
	protected List<Color> colors;

	protected Vector3f position;
	protected Vector3f rotation;
	protected Vector3f scale;

	protected Color defaultColor;

	protected boolean visible = true;

	private AtomicBoolean changed = new AtomicBoolean(false);

	public Model() {
		this.setVertices(new LinkedHashMap<String, Vector3f>());
		this.setIndices(new ArrayList<>());
		this.setNormals(new ArrayList<>());
		this.setColors(new ArrayList<>());
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.scale = new Vector3f(1, 1, 1);
		this.setDefaultLod();
	}

	public Map<String, Vector3f> getVertices() {
		return vertices;
	}

	public void setVertices(Map<String, Vector3f> vertices) {
		this.vertices = vertices;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public int getNumberOfIndices() {
		return this.indices.size();
	}

	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

	public List<Vector3f> getNormals() {
		return normals;
	}

	public void setNormals(List<Vector3f> normals) {
		this.normals = normals;
	}

	public void setDefaultLod() {
		this.lod = LOD_STAGES[LOD_STAGES.length - 1];
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void changeRotation(Vector3f rot) {
		this.rotation.x += rot.x;
		this.rotation.y += rot.y;
		this.rotation.z += rot.z;
	}

	public void changePosition(Vector3f pos) {
		this.position.x += pos.x;
		this.position.y += pos.y;
		this.position.z += pos.z;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void changeScale(Vector3f scale) {
		this.scale.x += scale.x;
		this.scale.y += scale.y;
		this.scale.z += scale.z;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean hasChanged() {
		return changed.get();
	}

	public void setChanged(boolean changed) {
		this.changed.set(changed);
	}

	public void addVertex(Vector3f vertex) {
		this.addVertex(String.valueOf(this.vertices.size()), vertex);
	}

	public void addVertex(String identifier, Vector3f vertex) {
		if (DataInspector.notNull(vertex)) {
			if (this.containsVertex(identifier)) {
				this.addIndex(this.getVertexIndex(vertex));
			} else {
				this.vertices.put(identifier, vertex);
				this.addIndex(this.vertices.size() - 1);
			}
		}
	}

	public boolean containsVertex(Vector3f vertex) {
		return this.getVertexIndex(vertex) > -1;
	}

	public boolean containsVertex(String identifier) {
		return this.vertices.keySet().contains(identifier);
	}

	public Vector3f getVertex(String identifier) {
		return this.containsVertex(identifier) ? this.vertices.get(identifier) : null;
	}

	public int getVertexIndex(Vector3f vertex) {
		int i = 0;
		for (String identifier : this.vertices.keySet()) {
			Vector3f v = this.vertices.get(identifier);
			if (DataInspector.notNull(v)) {
				if (v.x == vertex.x && v.y == vertex.y && v.z == vertex.z) {
					return i;
				}
			}
			i++;
		}
		return -1;
	}

	private void addIndex(int index) {
		if (index > -1) {
			this.indices.add(index);
		}
	}

	public void addNormal(Vector3f normal) {
		this.normals.add(normal);
	}

	public void addColor(Color color) {
		this.colors.add(color);
	}

	public FloatBuffer getVertexBuffer() {
		FloatBuffer vData = BufferUtils.createFloatBuffer(this.vertices.size() * 3);
		for (Vector3f vertex : this.vertices.values()) {
			vData.put(vertex.x).put(vertex.y).put(vertex.z);
		}
		vData.flip();
		return vData;
	}

	public IntBuffer getIndexBuffer() {
		IntBuffer iData = BufferUtils.createIntBuffer(this.indices.size());
		for (Integer index : this.indices) {
			iData.put(index);
		}
		iData.flip();
		return iData;
	}

	public FloatBuffer getColorBuffer() {
		FloatBuffer cData = BufferUtils.createFloatBuffer(this.colors.size() * 4);
		for (Color color : this.colors) {
			cData.put(color.getRed() / 255f).put(color.getGreen() / 255f).put(color.getBlue() / 255f)
					.put(color.getAlpha() / 255f);
		}
		cData.flip();
		return cData;
	}

	public FloatBuffer getNormalBuffer() {
		FloatBuffer nData = BufferUtils.createFloatBuffer(this.normals.size() * 3);
		for (Vector3f normal : this.normals) {
			nData.put(normal.x).put(normal.y).put(normal.z);
		}
		nData.flip();
		return nData;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> color) {
		this.colors = color;
	}

	public Color getDefaultColor() {
		return this.defaultColor;
	}

	public void setDefaultColor(Color color) {
		this.defaultColor = color;
	}

	public void update(Object... data) {
	}

	protected void resetModelData() {
		this.vertices.clear();
		this.normals.clear();
		this.indices.clear();
		this.colors.clear();
	}

	public Model clone() {
		Model model = new Model();
		Vector3f rotation = new Vector3f(this.rotation.x, this.rotation.y, this.rotation.z);
		Vector3f position = new Vector3f(this.position.x, this.position.y, this.position.z);
		Vector3f scale = new Vector3f(this.scale.x, this.scale.y, this.scale.z);
		Color defColor = new Color(this.defaultColor.getRed(), this.defaultColor.getGreen(),
				this.defaultColor.getBlue(), this.defaultColor.getAlpha());
		model.setRotation(rotation);
		model.setScale(scale);
		model.setPosition(position);
		model.setVisible(this.isVisible());
		model.setChanged(false);
		model.setDefaultColor(defColor);
		return model;
	}

}
