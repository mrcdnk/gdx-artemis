package de.verygame.square.game.artemis.system.component.rendering;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import de.verygame.square.game.artemis.system.component.RectTransform;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 02.04.2016.
 */
public abstract class RGBADrawable extends Drawable {

    private float alpha = 1;
    private float red = 1;
    private float green = 1;
    private float blue = 1;

    public RGBADrawable(RectTransform transform){
        super(transform);
    }

    public RGBADrawable(RectTransform transform, float red, float green, float blue, float alpha){
        super(transform);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    @Override
    public void preDraw(PolygonSpriteBatch batch, float offsetX, float offsetY){
        batch.setColor(red, green, blue, alpha);
    }

    @Override
    public void preDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height){
        batch.setColor(red, green, blue, alpha);
    }

    @Override
    public void postDraw(PolygonSpriteBatch batch, float offsetX, float offsetY){
        batch.setColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void postDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height){
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public void setAlpha(float alpha){
        this.alpha = alpha;
    }

    public float getAlpha(){
        return alpha;
    }
}
