package com.base.engine.rendering;

import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.base.common.IRenderer;
import com.base.common.resources.DataElement;
import com.base.engine.Engine;
import com.base.engine.RenderUtils;
import com.base.engine.font.OLD_STUFF.FontManager;
import com.base.engine.font.OLD_STUFF.FontRenderer;
import com.base.engine.font.OLD_STUFF.TrueTypeFont;

public abstract class AHoverDataRenderer implements IRenderer {

	protected DataElement hoverData = null;
	protected float x, y;

	protected TrueTypeFont trueTypeFont;

	protected float width = 0.5f, height = -0.35f;

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

		float w = Display.getWidth();
		float h = Display.getHeight();

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.9f);
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
            
		RenderUtils.switch2D(-500, -500, 500, 500);

		// Custom font rendering
		
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
