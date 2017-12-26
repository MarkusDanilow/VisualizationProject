package com.base.engine.rendering;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

import com.base.common.IRenderer;
import com.base.common.resources.DataElement;
import com.base.engine.Engine;
import com.base.engine.font.NEW.NewFontManager;
import com.base.engine.font.OLD_STUFF.TrueTypeFont;

public abstract class AHoverDataRenderer implements IRenderer {

	protected DataElement hoverData = null;
	protected float x, y;

	protected TrueTypeFont trueTypeFont;

	protected float width = 0.7f, height = -0.7f;

	public AHoverDataRenderer() {
		String fontName = "Agent Orange";
		if (!TrueTypeFont.isSupported(fontName))
			fontName = "serif";
		Font font = new Font(fontName, Font.ITALIC | Font.BOLD, 20);
		trueTypeFont = new TrueTypeFont(font, true);
	}

	public DataElement getHoverData() {
		return hoverData;
	}

	public void setHoverData(DataElement hoverData) {
		this.hoverData = hoverData;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = -y;
	}

	protected void renderFrame() {

		if ((x + width) < -1) {
			x += Math.abs(x + width) - 1;
		} else if ((x + width) > 1) {
			x -= Math.abs(x + width) - 1;
		}

		if ((y + height) < -1) {
			y += Math.abs(y + height) - 1;
		} else if ((y + height) > 1) {
			y -= Math.abs(y + height) - 1;
		}

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glColor4f(0.1f, 0.1f, 0.1f, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_BLEND);

		GL11.glLineWidth(1);
		GL11.glColor4f(0.95f, 0.95f, 0.95f, 0.9f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glVertex2f(x, y);
		GL11.glEnd();

		float px = x * NewFontManager.SCALE;
		float py = (y + height) * 500;
		float sx = 15, sy = 20;

		NewFontManager.prepare();

		// Custom font rendering
		NewFontManager.renderText(px + sx, py + sy, 12, 3, "Datenpunkt");
		if (this.hoverData.isSampled()) {
			NewFontManager.renderText(px + sx, py + sy * 3f, 10, 3, "Elemente: " + this.hoverData.getSampleRate());
			NewFontManager.renderText(px + sx, py + sy * 5f, 10, 3, "Zeitraum: "
					+ this.hoverData.getTimeRange().getLoVal() + " bis " + this.hoverData.getTimeRange().getHiVal());
		} else {
			NewFontManager.renderText(px + sx, py + sy * 3f, 10, 3, "Elemente: 1");
			NewFontManager.renderText(px + sx, py + sy * 5f, 10, 3, "Zeitpunkt: " + this.hoverData.getTime());
		}
		NewFontManager.renderText(px + sx, py + sy * 7f, 10, 3, "X: " + this.hoverData.getX());
		NewFontManager.renderText(px + sx, py + sy * 9f, 10, 3, "Y: " + this.hoverData.getY());
		NewFontManager.renderText(px + sx, py + sy * 11f, 10, 3, "Z: " + this.hoverData.getZ());

		NewFontManager.close();

	}

	@Override
	public void render(Object... objects) {
		this.renderFrame();
	}

	/*
	 * -------------------------------------------------------------------------
	 * ------------- den ganzen Mumpitz, der ab hier kommt, brauchen wir bei den
	 * Hover Renderern nicht!
	 * -------------------------------------------------------------------------
	 * -------------
	 */

	@Override
	public boolean is3D() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAffectedByCameraPos() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAffectedByCameraAngle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] createCustomViewport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object selectRenderData(Engine engine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toggleHover(boolean state) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isHoverActivated() {
		// TODO Auto-generated method stub
		return false;
	}
}
