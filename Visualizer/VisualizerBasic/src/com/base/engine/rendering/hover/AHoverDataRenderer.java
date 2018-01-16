package com.base.engine.rendering.hover;

import org.lwjgl.opengl.GL11;

import com.base.common.IRenderer;
import com.base.common.resources.DataElement;
import com.base.engine.Engine;
import com.base.engine.RenderUtil;
import com.base.engine.Settings;
import com.base.engine.font.NEW.NewFontManager;

public abstract class AHoverDataRenderer implements IRenderer {

	public static DataElement activeElement = null;

	protected DataElement hoverData = null;
	protected float x, y, tx, ty;

	protected float width = 0.7f, height = -0.7f;

	public DataElement getHoverData() {
		return hoverData;
	}

	public void setHoverData(DataElement hoverData) {
		this.hoverData = hoverData;
		activeElement = hoverData;
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

		GL11.glColor4f(0.8f, 0.8f, 0.8f, 1f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(-1, y);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, 1);
		GL11.glEnd();

		tx = x ; 
		ty = y ; 
		
		if ((tx + width) < -1) {
			tx += Math.abs(tx + width) - 1;
		} else if ((tx + width) > 1) {
			tx -= Math.abs(tx + width) - 1;
		}

		if ((ty + height) < -1) {
			ty += Math.abs(ty + height) - 1;
		} else if ((ty + height) > 1) {
			ty -= Math.abs(ty + height) - 1;
		}

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glColor4f(Settings.HOVER_BG_COLOR.getRed(), Settings.HOVER_BG_COLOR.getGreen(),
				Settings.HOVER_BG_COLOR.getBlue(), Settings.HOVER_BG_COLOR.getAlpha());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(tx, ty);
		GL11.glVertex2f(tx + width, ty);
		GL11.glVertex2f(tx + width, ty + height);
		GL11.glVertex2f(tx, ty + height);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_BLEND);

		GL11.glLineWidth(1);
		GL11.glColor4f(Settings.HOVER_BORDER_COLOR.getRed(), Settings.HOVER_BORDER_COLOR.getGreen(),
				Settings.HOVER_BORDER_COLOR.getBlue(), Settings.HOVER_BORDER_COLOR.getAlpha());
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2f(tx, ty);
		GL11.glVertex2f(tx + width,ty);
		GL11.glVertex2f(tx + width, ty + height);
		GL11.glVertex2f(tx, ty + height);
		GL11.glVertex2f(tx,ty);
		GL11.glEnd();

		float px = tx * NewFontManager.SCALE;
		float py = (ty + height) * 500;
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
		NewFontManager.renderText(px + sx, py + sy * 13f, 10, 3, "Lat: " + this.hoverData.getRealLat());
		NewFontManager.renderText(px + sx, py + sy * 15f, 10, 3, "Lng: " + this.hoverData.getRealLng());

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
