package de.verygame.square.game.artemis.system.component.rendering;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.util.ArrayUtils;
import de.verygame.square.util.PolygonUtils;

/**
 * Created by Marco on 19.04.2017.
 *
 * @author Marco Deneke
 */

public class PolygonTexture extends RGBADrawable {
    private PolygonRegion polygonRegion;

    public PolygonTexture(TextureRegion textureRegion, RectTransform transform, Vector2[] polygon, short[] triangles) {
        super(transform);
        this.polygonRegion = new PolygonRegion(textureRegion, ArrayUtils.buildVertexArray(polygon), triangles);
    }

    public PolygonTexture(TextureRegion textureRegion, RectTransform transform, Vector2[] polygon) {
        this(textureRegion, transform, ArrayUtils.buildVertexArray(polygon));
    }

    public PolygonTexture(TextureRegion textureRegion, RectTransform transform, float[] polygon){
        super(transform);
        this.polygonRegion = new PolygonRegion(textureRegion, polygon, PolygonUtils.getDefaultTriangles(polygon));
    }

    @Override
    public void onDraw(PolygonSpriteBatch batch, float offsetX, float offsetY) {
        batch.draw(polygonRegion, rectTransform.getX() + offsetX, rectTransform.getY() + offsetY, rectTransform.getWidth() * rectTransform.getWidthScale(), rectTransform.getHeight() * rectTransform.getHeightScale());
    }

    @Override
    void onDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height) {
        float rotationAngle = usesRotation ? getCenteredRotation() : 0;

        batch.draw(polygonRegion, rectTransform.getX() + offsetX, rectTransform.getY() + offsetY, width/2, height/2, width, height, 1f, 1f, rotationAngle);
    }

    @Override
    protected void calculateOffset() {
            //not perfect but works for now and can be changed when needed
            float[] center = PolygonUtils.calculateCenter(PolygonUtils.scaleToSize(polygonRegion.getVertices(),
                    rectTransform.getWidth() + rectTransform.getWidthScale(), rectTransform.getHeight() * rectTransform.getHeightScale()));
            this.centeredXOffset = center[0] - (centeredWidth/2);
            this.centeredYOffset = center[1] - (centeredHeight/2);
    }
}
