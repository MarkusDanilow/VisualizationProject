package com.base.world;

import static org.lwjgl.opengl.GL11.glColor4f;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import com.base.common.resources.DataInspector;
import com.base.engine.RenderUtil;
import com.base.engine.WavefrontModelLoader;
import com.base.world.model.Model;

public class World {

	private static boolean WIREFRAME = true;

	public static final String ID_TERRAIN = "terrain", ID_CHUNK = "chunk";

	public static float FIXED_CAMERA_HEIGHT = -50;

	private static ModelPool models, blueprints;

	public static void create() {
		models = new ModelPool();
		blueprints = new ModelPool();
		prepareModels();
	}

	public static void addModel(String identifier, Model model) {
		models.addModel(identifier, model);
	}

	public static void addModelFromBlueprints(String blueprintIdentifer, String modelIdentifier, Vector3f position,
			Vector3f rotation, Vector3f scale) {
		if (blueprintExists(blueprintIdentifer)) {
			System.out.println("blueprint found: " + blueprintIdentifer);
			Model model = blueprints.get(blueprintIdentifer).clone();
			model.setPosition(position);
			model.setRotation(rotation);
			model.setScale(scale);

			models.getModels().put(modelIdentifier, model);
			models.getVertexBufferIDs().put(modelIdentifier, blueprints.getVertexBufferID(blueprintIdentifer));
			models.getNormalBufferIDs().put(modelIdentifier, blueprints.getNormalBufferID(blueprintIdentifer));
			models.getIndexBufferIDs().put(modelIdentifier, blueprints.getIndexBufferID(blueprintIdentifer));
			models.getColorBufferIDs().put(modelIdentifier, blueprints.getColorBufferID(blueprintIdentifer));
			models.getNumberOfIndices().put(modelIdentifier, blueprints.getNumberOfIndices(blueprintIdentifer));

		}
	}

	public static void removeModel(String identifier) {
		models.removeModel(identifier);
	}

	public static boolean modelExists(String identifier) {
		return DataInspector.notNull(models.get(identifier));
	}

	public static void addBlueprint(String identifier, Model model) {
		blueprints.addModel(identifier, model);
		System.out.println("Added blueprint: " + identifier);
	}

	public static void removeBlueprint(String identifier) {
		blueprints.removeModel(identifier);
	}

	public static boolean blueprintExists(String identifier) {
		return DataInspector.notNull(blueprints.get(identifier));
	}

	public static void render() {
		RenderUtil.pushMatrix();
		RenderUtil.translate(new Vector3f(32700, 0, 32700));
		RenderUtil.rotate(new Vector3f(0, -90, 0));
		RenderUtil.scale(new Vector3f(3300, 3300, 3300));
		for (String identifier : models.keySet()) {
			Model model = models.get(identifier);
			if (model.isVisible()) {
				RenderUtil.pushMatrix();
				glColor4f(1,0,0,0.5f);
				RenderUtil.transform(model.getPosition(), model.getRotation(), model.getScale());
				RenderUtil.renderIBO(models.getVertexBufferID(identifier), -1, models.getIndexBufferID(identifier),
						models.getColorBufferID(identifier), models.getNumberOfIndices(identifier));
				RenderUtil.popMatrix();
			}
		}
		RenderUtil.popMatrix();
	}

	public static void prepareModels() {
		String[] models = new String[] { 
				"buildings/geb1",
				"buildings/geb2-5",
				"buildings/geb3-4",
				"buildings/geb6",
				"buildings/geb7",
				"buildings/geb8",
				"buildings/geb9",
				"buildings/geb10",
				"buildings/geb11",
				"buildings/geb12",
				"buildings/geb13",
				"buildings/geb14",
				"buildings/geb15",
				"buildings/geb17",
				"buildings/geb20"};
		for (String s : models) {
			Model m = WavefrontModelLoader.load(s);
			addBlueprint(s, m);
			addModelFromBlueprints(s, "model_" + s, new Vector3f(0,0,0),
					new Vector3f(0,0,0), new Vector3f(1,1,1));
		}
	}

	public static boolean isWireframe() {
		return WIREFRAME;
	}

	public static void setWireframe(boolean wireframe) {
		WIREFRAME = wireframe;
	}

}
