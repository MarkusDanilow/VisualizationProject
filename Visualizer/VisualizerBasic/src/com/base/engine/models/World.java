package com.base.engine.models;

import org.lwjgl.util.vector.Vector3f;

import com.base.common.resources.DataInspector;
import com.base.engine.RenderUtil;

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

	public static Model getModelByName(String name) {
		return models.getModels().get(name);
	}

	public static void movePlayer(float x, float y, float z) {
		getModelByName("player").setPosition(new Vector3f(x, y, z));
	}

	public static boolean modelExists(String identifier) {
		return DataInspector.notNull(models.get(identifier));
	}

	public static void addBlueprint(String identifier, Model model) {
		blueprints.addModel(identifier, model);
	}

	public static void removeBlueprint(String identifier) {
		blueprints.removeModel(identifier);
	}

	public static boolean blueprintExists(String identifier) {
		return DataInspector.notNull(blueprints.get(identifier));
	}

	public static void render() {
		RenderUtil.pushMatrix();

		for (String identifier : models.keySet()) {
			Model model = models.get(identifier);
			if (model.isVisible()) {
				RenderUtil.pushMatrix();

				RenderUtil.transform(model.getPosition(), model.getRotation(), model.getScale());
				RenderUtil.renderIBO(models.getVertexBufferID(identifier), -1, models.getIndexBufferID(identifier),
						models.getColorBufferID(identifier), models.getNumberOfIndices(identifier));
				RenderUtil.popMatrix();
			}
		}
		RenderUtil.popMatrix();
	}

	public static void prepareModels() {

		// load buildings
		String[] models = new String[] { "geb1", "geb2-5", "geb3-4", "geb6", "geb7", "geb8", "geb9", "geb10", "geb11",
				"geb12", "geb13", "geb14", "geb15", "geb17", "geb20", "container" };
		for (String s : models) {
			Model m = WavefrontModelLoader.load("buildings/" + s, BuildingModel.class.getSimpleName());
			addBlueprint(s, m);
			addModelFromBlueprints(s, s, new Vector3f(0, 0, 0), new Vector3f(0, -90, 0),
					new Vector3f(3300, 3300, 3300));
		}

		// load player
		String manName = "player";
		Model m = WavefrontModelLoader.load(manName, PlayerModel.class.getSimpleName());
		addBlueprint(manName, m);
		addModelFromBlueprints(manName, manName, new Vector3f(30000, 0, 30000), new Vector3f(90, 0, 0),
				new Vector3f(10, 10, 10));
	}

	public static boolean isWireframe() {
		return WIREFRAME;
	}

	public static void setWireframe(boolean wireframe) {
		WIREFRAME = wireframe;
	}

}
