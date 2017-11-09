package com.base.engine;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.base.common.resources.Range;

final class CommandInterpreter {

	private static enum Commands {

		HELP("help", "shows a list of all commands"), EXIT("exit", "closes the application"), RESET_DISPLAY_LISTS(
				"reset display lists", "resets all display lists"), CHANGE_RENDER_MODE("change render mode",
						"changes the render mode. Parameters: [Render Modes]"), CHANGE_SPEED("change speed",
								"changes the speed of the camera. Parameter: FLOAT"), TOGGLE_ANIMATION(
										"toggle animation", "toggles the animation mode"), TOGGLE_HELP("toggle help",
												"toggles the list of all commands"), TOGGLE_GRID("toggle grid",
														"toggles the grid visibility"), TOGGLE_WATER("toggle water",
																"toggles terrain render mode: complete / only above water (0)"), TOGGLE_VEGETATION(
																		"toggle vegetation",
																		"toggles the vegetation"), PERLIN_NOISE_2D(
																				"perlin noise 2",
																				"generates perlin noise 2D. Parameters: [Generation Parameters]"), PERLIN_NOISE_3D(
																						"perlin noise 3",
																						"generates perlin noise 3D. Parameters: [Generation Parameters]"), RANDOM_NOISE(
																								"random noise",
																								"generates a random noise. Parameters: [Generation Parameters]"), MIDPOINT_DISPLACEMENT(
																										"midpoint displacement",
																										"generates midpoint displacement. Parameters: [Generation Parameters]"), DIAMOND_SQUARE(
																												"diamond square",
																												"generates diamond square. Parameters: [Generation Parameters]"), SIMPLEX_NOISE_2D(
																														"simplex noise 2",
																														"generate simplex noise 2D. Parameters: [Generation Parameters]"), SIMPLEX_NOISE_3D(
																																"simplex noise 3",
																																"generates simplex noise 3D. Parameters: [Generation Parameters]"), SMOOTH(
																																		"smooth",
																																		"smoothes the terrain. Parameter: INTEGER"), LOAD(
																																				"load",
																																				"loads a heihgtmap from file system. Parameter: Path to image in file system");

		final String commandStr;
		final String hint;

		private Commands(final String commandStr, final String hint) {
			this.commandStr = commandStr;
			this.hint = hint;
		}

		String removeCommand(String cmd) {
			return cmd.replace(this.commandStr, "").trim();
		}

		static Commands getByCommandStr(String commandStr) {
			if (commandStr != null) {
				int index = getIndexByCommandStr(commandStr);
				if (index > -1) {
					return Commands.values()[index];
				}
			}
			return null;
		}

		static int getIndexByCommandStr(String commandStr) {
			if (commandStr != null) {
				commandStr = commandStr.toLowerCase().trim();
				for (int i = 0; i < Commands.values().length; i++) {
					if (commandStr.startsWith(Commands.values()[i].commandStr)) {
						String c = commandStr.substring(Commands.values()[i].commandStr.length());
						if (Commands.values()[i].commandStr.equals(commandStr.replace(c, ""))) {
							return i;
						}
					} else if (Commands.values()[i].commandStr.startsWith(commandStr)) {
						return i;
					}
				}
			}
			return -1;
		}

	}

	static final String RENDER_MODE_POINTS = "GL_POINTS", RENDER_MODE_LINES = "GL_LINES",
			RENDER_MODE_TRIANGLES = "GL_TRIANGLES", RENDER_MODE_QUADS = "GL_QUADS";

	static List<String> renderModes = new ArrayList<String>();

	static {
		renderModes.add(RENDER_MODE_POINTS);
		renderModes.add(RENDER_MODE_LINES);
		renderModes.add(RENDER_MODE_TRIANGLES);
		renderModes.add(RENDER_MODE_QUADS);
	}

	private static EngineInterfaces engine;

	static void create(EngineInterfaces engine) {
		CommandInterpreter.engine = engine;
	}

	static void execute(String command) {
		final Command c = getCommandByString(command);
		if (c != null) {
			new Thread() {
				public void run() {
					c.execute();
				}
			}.start();
		}
	}

	private static interface Command {
		void execute();
	}

	private static Command getCommandByString(String commandStr) {
		if (commandStr != null) {
			commandStr = commandStr.toLowerCase().trim();
			Commands cmd = Commands.getByCommandStr(commandStr);
			if (cmd != null) {
				switch (cmd) {
				case HELP:
					return new CommandHelp();
				case EXIT:
					return new CommandExit();
				case RESET_DISPLAY_LISTS:
					return new CommandResetDisplayList();
				case CHANGE_RENDER_MODE:
					return new CommandChangeRenderMode(Commands.CHANGE_RENDER_MODE.removeCommand(commandStr));
				case CHANGE_SPEED:
					return new CommandChangeSpeed(Commands.CHANGE_SPEED.removeCommand(commandStr));
				case TOGGLE_ANIMATION:
					return new CommandToggleAnimation();
				case TOGGLE_HELP:
					return new CommandToggleHelp();
				case TOGGLE_GRID:
					return new CommandToggleGrid();
				case TOGGLE_WATER:
					return new CommandToggleWater();
				case TOGGLE_VEGETATION:
					return new CommandToggleVegetation();
				case RANDOM_NOISE:
					return new CommandRandomNoise(Commands.RANDOM_NOISE.removeCommand(commandStr));
				case PERLIN_NOISE_2D:
					return new CommandPerlinNoise2D(Commands.PERLIN_NOISE_2D.removeCommand(commandStr));
				case PERLIN_NOISE_3D:
					return new CommandPerlinNoise3D(Commands.PERLIN_NOISE_3D.removeCommand(commandStr));
				case MIDPOINT_DISPLACEMENT:
					return new CommandMidpointDisplacement(Commands.MIDPOINT_DISPLACEMENT.removeCommand(commandStr));
				case DIAMOND_SQUARE:
					return new CommandDiamondSquare(Commands.DIAMOND_SQUARE.removeCommand(commandStr));
				case SIMPLEX_NOISE_2D:
					return new CommandSimplexNoise2D(Commands.SIMPLEX_NOISE_2D.removeCommand(commandStr));
				case SIMPLEX_NOISE_3D:
					return new CommandSimplexNoise3D(Commands.SIMPLEX_NOISE_3D.removeCommand(commandStr));
				case SMOOTH:
					return new CommandSmoothTerrain(Commands.SMOOTH.removeCommand(commandStr));
				case LOAD:
					return new CommandLoadHeightmap(Commands.LOAD.removeCommand(commandStr));
				default:
					break;
				}
			}
		}
		return null;
	}

	private static int getRenderModeByString(String renderMode) {
		if (renderMode != null) {
			renderMode = renderMode.toUpperCase().trim();
			if (renderMode.equals(RENDER_MODE_LINES)) {
				return GL_LINES;
			}
			if (renderMode.equals(RENDER_MODE_POINTS)) {
				return GL_POINTS;
			}
			if (renderMode.equals(RENDER_MODE_QUADS)) {
				return GL_QUADS;
			}
			if (renderMode.equals(RENDER_MODE_QUADS)) {
				return GL_TRIANGLES;
			}
		}
		return -1;
	}

	static String getNextCompleteCommand(String cmdLine) {
		if (cmdLine != null) {
			int index = Commands.getIndexByCommandStr(cmdLine);
			if (index > -1) {
				if (Commands.values()[index].commandStr.equals(cmdLine)) {
					index++;
				}
				index = index >= Commands.values().length ? 0 : index;
				return Commands.values()[index].commandStr;
			}
		}
		return "";
	}

	/*
	 * ---------------------------------- COMMAND CLASSES
	 * ----------------------------------
	 */

	private static class CommandHelp implements Command {
		@Override
		public void execute() {
			StringBuilder hints = new StringBuilder();

			hints.append("Keyboard shortcuts: \n");
			hints.append(" > F1: show / hide user interface\n");
			hints.append(" > F3: show / hide console\n");
			hints.append(" > F11: enable / disable fullscreen \n");
			hints.append(" > ESCAPE: hide console / close application\n");

			hints.append("\n");

			for (Commands c : Commands.values()) {
				hints.append("<" + c.commandStr + "> " + c.hint + "\n");
			}

			hints.append("\n");

			hints.append("Render Modes: GL_POINTS, GL_LINES, GL_TRIANGLES, GL_QUADS\n");
			hints.append("Generation Parameters ( need to be separated by < , > ) : \n");
			hints.append(" > w: terrain size\n");
			hints.append(" > s: terrain smoothness\n");
			hints.append(" > r: terrain roughness (only for Perlin and Simplex)\n");
			hints.append(" > r0: lower height limit\n");
			hints.append(" > r1: upper height limit");

			engine.updateHints(hints.toString());
		}
	}

	private static class CommandExit implements Command {
		@Override
		public void execute() {
			engine.setRunning(false);
		}
	}

	private static class CommandResetDisplayList implements Command {
		@Override
		public void execute() {
			engine.resetDisplayLists();
		}
	}

	private static class CommandToggleAnimation implements Command {
		@Override
		public void execute() {
			engine.toggleAnimation();
		}
	}

	private static class CommandToggleHelp implements Command {
		@Override
		public void execute() {
			new CommandHelp().execute();
		}
	}

	private static class CommandToggleGrid implements Command {
		@Override
		public void execute() {
			engine.toggleGrid();
		}
	}

	private static class CommandToggleWater implements Command {
		@Override
		public void execute() {
			engine.toggleWater();
		}
	}

	private static class CommandToggleVegetation implements Command {
		@Override
		public void execute() {
			engine.toggleVegetation();
		}
	}

	private static abstract class CommandWithOptions implements Command {
		protected final String commandOptions;
		protected final String[] options;

		public CommandWithOptions(final String generateOptions) {
			this.commandOptions = generateOptions;
			this.options = this.commandOptions.split(",");
		}

		public String[] getOptions() {
			return options;
		}

		boolean optionsExist() {
			return optionExists(0);
		}

		boolean optionExists(int index) {
			return options != null && options.length > index && options[index] != null;
		}
	}

	private static abstract class GenerationCommand extends CommandWithOptions {
		protected int size = 512, roughness = 5, smoothness = -1;
		protected Range<Float> range = new Range<Float>(-1f, 1f);

		public GenerationCommand(String generateOptions) {
			super(generateOptions);
			extractAll();
		}

		int extractSize(String s) {
			size = Integer.parseInt(s.trim());
			return size;
		}

		float extractRangeValue(String s) {
			return Float.parseFloat(s.trim());
		}

		Range<Float> extractRange(float lo, float hi) {
			range = new Range<Float>(lo, hi);
			return range;
		}

		int extractRoughness(String s) {
			roughness = Integer.parseInt(s.trim());
			return roughness;
		}

		int extractSmoothness(String s) {
			smoothness = Integer.parseInt(s.trim());
			return smoothness;
		}

		void extractAll() {
			float lo = (float) Math.PI, hi = (float) Math.PI;
			for (String option : options) {
				option = option.trim();
				String[] s = option.split("\\s+");
				s[0] = s[0].replace("-", "");
				if (s[0].equals("w")) {
					extractSize(s[1]);
				} else if (s[0].equals("r")) {
					extractRoughness(s[1]);
				} else if (s[0].equals("s")) {
					extractSmoothness(s[1]);
				} else if (s[0].equals("r0")) {
					lo = extractRangeValue(s[1]);
				} else if (s[0].equals("r1")) {
					hi = extractRangeValue(s[1]);
				}
			}
			if (lo != (float) Math.PI && hi != (float) Math.PI) {
				extractRange(lo, hi);
			}
		}
	}

	private static class CommandChangeRenderMode extends CommandWithOptions {
		public CommandChangeRenderMode(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.changeRenderMode(getRenderModeByString(this.commandOptions));
			engine.resetDisplayLists();
		}
	}

	private static class CommandChangeSpeed extends CommandWithOptions {
		public CommandChangeSpeed(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.changeCameraSpeed(Float.parseFloat(this.commandOptions));
		}
	}

	private static class CommandPerlinNoise2D extends GenerationCommand {
		public CommandPerlinNoise2D(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.generatePerlinNoise2D(size, size, roughness, range, smoothness);
			engine.resetDisplayLists();
		}
	}

	private static class CommandPerlinNoise3D extends GenerationCommand {
		public CommandPerlinNoise3D(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.generatePerlinNoise3D(size, size, (int) (range.getHiVal() - range.getLoVal()), roughness,
					smoothness);
			engine.resetDisplayLists();
		}
	}

	private static class CommandRandomNoise extends GenerationCommand {
		public CommandRandomNoise(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.generateRandomNoise(size, size, range, smoothness);
			engine.resetDisplayLists();
		}
	}

	private static class CommandMidpointDisplacement extends GenerationCommand {
		public CommandMidpointDisplacement(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.generateMidpointDisplacement(size, range, smoothness);
			engine.resetDisplayLists();
		}
	}

	private static class CommandDiamondSquare extends GenerationCommand {
		public CommandDiamondSquare(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.generateDiamondSquare(size, range, smoothness);
			engine.resetDisplayLists();
		}
	}

	private static class CommandSimplexNoise2D extends GenerationCommand {
		public CommandSimplexNoise2D(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.generateSimplexNoise2D(size, size, roughness, range, smoothness);
			engine.resetDisplayLists();
		}
	}

	private static class CommandSimplexNoise3D extends GenerationCommand {
		public CommandSimplexNoise3D(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.generateSimplexNoise3D(size, size, (int) (range.getHiVal() - range.getLoVal()), roughness,
					smoothness);
			engine.resetDisplayLists();
		}

	}

	private static class CommandSmoothTerrain extends CommandWithOptions {
		public CommandSmoothTerrain(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			engine.smooth(Integer.parseInt(commandOptions));
		}
	}

	public static class CommandLoadHeightmap extends CommandWithOptions {
		public CommandLoadHeightmap(String generateOptions) {
			super(generateOptions);
		}

		@Override
		public void execute() {
			try {
				BufferedImage img = ImageIO.read(new File(this.commandOptions));
				engine.loadHeightmap(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
