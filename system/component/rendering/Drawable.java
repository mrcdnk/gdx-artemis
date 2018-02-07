package de.verygame.square.game.artemis.system.component.rendering;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import de.verygame.square.game.artemis.system.component.RectTransform;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 11.03.2016.
 *         Note: this class has a natural ordering that is inconsistent with equals.
 */
public abstract class Drawable implements Comparable<Drawable>{

    private Drawable centeredDrawable;

    protected RectTransform rectTransform;

    //used by this entity directly
    protected boolean usesRotation = false;
    protected float rotationAngle = 0;

    //used for the
    //relative to rectTransform size
    protected float centeredWidth;
    protected float centeredHeight;

    //Offset to center the child centeredDrawable
    protected float centeredXOffset;
    protected float centeredYOffset;

    protected int layerIndex = 1;


    /**
     * Basic drawable for drawing on the PolygonSpriteBatch.
     * Can be used as CenteredDrawable with rectTransform of the root.
     *
     * @param rectTransform RectTransform defining size and position for drawing or properties of the root Drawable.
     */
    public Drawable(RectTransform rectTransform) {
        this.rectTransform = rectTransform;
    }

    public void draw(PolygonSpriteBatch batch) {
        draw(batch, 0, 0);
    }

    public void draw(PolygonSpriteBatch batch, float offsetX, float offsetY) {
        preDraw(batch, offsetX, offsetY);
        onDraw(batch, offsetX, offsetY);
        if (centeredDrawable != null) {
            centeredDrawable.drawCentered(batch, offsetX + centeredXOffset,
                    offsetY + centeredYOffset, centeredWidth, centeredHeight);
        }
        postDraw(batch, offsetX, offsetY);
    }

    private void drawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height) {
        preDrawCentered(batch, offsetX, offsetY, width, height);
        onDrawCentered(batch, offsetX, offsetY, width, height);
        postDrawCentered(batch, offsetX, offsetY, width, height);

    }


    protected void preDraw(PolygonSpriteBatch batch, float offsetX, float offsetY) {
        //only for override
    }

    protected void preDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height) {
        //only for override
    }

    abstract void onDraw(PolygonSpriteBatch batch, float offsetX, float offsetY);

    void onDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height) {
        //only if centered drawable is used
    }

    protected void postDraw(PolygonSpriteBatch batch, float offsetX, float offsetY) {
        //only for override
    }

    protected void postDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height) {
        //only for override
    }

    /**
     * Adds a drawable at the center of the parent.
     *
     * @param drawable     Drawable to add
     * @param relativeSize relative to smallest between width and height root parent
     * @return The added drawable for chaining
     */
    public Drawable setCenteredDrawable(Drawable drawable, float relativeSize) {
        return setCenteredDrawable(drawable, relativeSize, false);
    }

    /**
     * Adds a drawable at the center of the parent.
     *
     * @param drawable     Drawable to add
     * @param relativeSize relative to smallest between width and height root parent
     * @param usesRotation enables rotation
     * @return The added drawable for chaining
     */
    public Drawable setCenteredDrawable(Drawable drawable, float relativeSize, boolean usesRotation) {
        this.centeredDrawable = drawable;
        float centeredSize = Math.min(relativeSize * rectTransform.getWidth(true), relativeSize * rectTransform.getHeight(true));
        this.centeredWidth = centeredSize;
        this.centeredHeight = centeredSize;
        drawable.usesRotation = usesRotation;
        calculateOffset();
        return drawable;
    }

    public void removeCenteredDrawable(){
        this.centeredDrawable = null;
    }

    public void setCenteredRotation(float rotationAngle) {
        if (centeredDrawable != null) {
            centeredDrawable.setCenteredRotation(rotationAngle);
        }
        if (usesRotation) {
            this.rotationAngle = rotationAngle;
        }
    }

    public float getCenteredRotation() {
        if (centeredDrawable != null) {
            return centeredDrawable.getCenteredRotation();
        }
        return this.rotationAngle;
    }

    protected void calculateOffset() {
        centeredXOffset = (rectTransform.getWidth() * rectTransform.getWidthScale() - centeredWidth) / 2;
        centeredYOffset = (rectTransform.getHeight() * rectTransform.getHeightScale() - centeredHeight) / 2;
    }

    public void setLayerIndex(int i){
        layerIndex = i;
    }

    public int getLayerIndex(){
        return layerIndex;
    }

    public Drawable getCenteredDrawable() {
        return centeredDrawable;
    }

    /**
     * Compares by LayerIndex
     * @param d other drawable
     * @return 0 if equal, -1 if this is smaller, 1 if this is bigger
     */
    @Override
    public int compareTo(Drawable d) {
        return Integer.compare(layerIndex, d.getLayerIndex());
    }
}
