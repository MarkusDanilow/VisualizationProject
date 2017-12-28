package com.base.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.base.common.resources.DataInspector;
import com.base.engine.RenderUtil;
import com.base.world.model.Model;

public class ModelPool {

	private Map<String, Model> models;
	private Map<String, Integer> vertexBufferIDs = new HashMap<>();
	private Map<String, Integer> normalBufferIDs = new HashMap<>();
	private Map<String, Integer> indexBufferIDs = new HashMap<>();
	private Map<String, Integer> colorBufferIDs = new HashMap<>();
	private Map<String, Integer> numberOfIndices = new HashMap<>();

	public ModelPool() {
		models = new HashMap<>();
	}

	public Map<String, Model> getModels() {
		return models;
	}

	public void setModels(Map<String, Model> models) {
		this.models = models;
	}

	public Map<String, Integer> getVertexBufferIDs() {
		return vertexBufferIDs;
	}

	public void setVertexBufferIDs(Map<String, Integer> vertexBufferIDs) {
		this.vertexBufferIDs = vertexBufferIDs;
	}

	public Map<String, Integer> getNormalBufferIDs() {
		return normalBufferIDs;
	}

	public void setNormalBufferIDs(Map<String, Integer> normalBufferIDs) {
		this.normalBufferIDs = normalBufferIDs;
	}

	public Map<String, Integer> getIndexBufferIDs() {
		return indexBufferIDs;
	}

	public void setIndexBufferIDs(Map<String, Integer> indexBufferIDs) {
		this.indexBufferIDs = indexBufferIDs;
	}

	public Map<String, Integer> getColorBufferIDs() {
		return colorBufferIDs;
	}

	public void setColorBufferIDs(Map<String, Integer> colorBufferIDs) {
		this.colorBufferIDs = colorBufferIDs;
	}

	public Map<String, Integer> getNumberOfIndices() {
		return numberOfIndices;
	}

	public void setNumberOfIndices(Map<String, Integer> numberOfIndices) {
		this.numberOfIndices = numberOfIndices;
	}
	
	public void addModel(String identifier, Model model) {
		if (DataInspector.notNull(identifier, model)) {
			
			int vboID, normalID, indexID, colorID;
			
			if (models.keySet().contains(identifier)) {
				
				vboID = vertexBufferIDs.get(identifier);
				normalID = normalBufferIDs.get(identifier);
				indexID = indexBufferIDs.get(identifier);
				colorID = colorBufferIDs.get(identifier);
				
			} else {
				
				vboID = RenderUtil.createBufferID();
				normalID = RenderUtil.createBufferID();
				indexID = RenderUtil.createBufferID();
				colorID = RenderUtil.createBufferID();

				models.put(identifier, model);
				vertexBufferIDs.put(identifier, vboID);
				normalBufferIDs.put(identifier, normalID);
				indexBufferIDs.put(identifier, indexID);
				colorBufferIDs.put(identifier, colorID);
				
			}
			
			numberOfIndices.put(identifier, model.getNumberOfIndices());
			
			RenderUtil.bindVertexBufferData(vboID, model.getVertexBuffer());
			RenderUtil.bindVertexBufferData(normalID, model.getNormalBuffer());
			RenderUtil.bindIndexBufferData(indexID, model.getIndexBuffer());
			RenderUtil.bindVertexBufferData(colorID, model.getColorBuffer());

		}
	}
	
	public void removeModel(String identifier){
		if (DataInspector.notNull(identifier)) {
			vertexBufferIDs.remove(identifier);
			normalBufferIDs.remove(identifier);
			indexBufferIDs.remove(identifier);
			colorBufferIDs.remove(identifier);
			numberOfIndices.remove(identifier);
			models.remove(identifier);
		}
	}

	public Model get(Object arg0) {
		return models.get(arg0);
	}

	public Set<String> keySet() {
		return models.keySet();
	}

	public int getVertexBufferID(String identifier){
		return this.vertexBufferIDs.get(identifier);
	}
	
	public int getNormalBufferID(String identifier){
		return this.normalBufferIDs.get(identifier);
	}
	
	public int getColorBufferID(String identifier){
		return this.colorBufferIDs.get(identifier);
	}
	
	public int getIndexBufferID(String identifier){
		return this.indexBufferIDs.get(identifier);
	}

	public int getNumberOfIndices(String identifier){
		return this.numberOfIndices.get(identifier);
	}

}
