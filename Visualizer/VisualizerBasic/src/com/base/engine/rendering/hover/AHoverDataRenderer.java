package com.base.engine.rendering.hover;

import org.lwjgl.opengl.GL11;

import com.base.common.IRenderer;
import com.base.common.resources.DataElement;
import com.base.engine.Engine;
import com.base.engine.RenderUtil;
import com.base.engine.Settings;
import com.base.engine.font.NEW.NewFontManager;

public abstract class AHoverDataRenderer implements IRenderer {

	public static DataElement activeElement = null ; 
	
	protected DataElement hoverData = null;
	protected float x, y;

	protected float width = 0.7f, height = -0.7f;

	public DataElement getHoverData() {
		return hoverData;
	}

	public void setHoverData(DataElement hoverData) {
		this.hoverData = hoverData;
		activeElement = hoverData ;
		Engine.resetAllViewports();
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
		
		RenderUtil.switch2D(-1, -1, 1, 1);

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

		GL11.glColor4f(Settings.HOVER_BG_COLOR.getRed(), Settings.HOVER_BG_COLOR.getGreen(),
				Settings.HOVER_BG_COLOR.getBlue(), Settings.HOVER_BG_COLOR.getAlpha());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_BLEND);

		GL11.glLineWidth(1);
		GL11.glColor4f(Settings.HOVER_BORDER_COLOR.getRed(), Settings.HOVER_BORDER_COLOR.getGreen(),
				Settings.HOVER_BORDER_COLOR.getBlue(), Settings.HOVER_BORDER_COLOR.getAlpha());
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
