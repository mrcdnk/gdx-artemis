package de.verygame.square.game.artemis.system.component.rendering;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.verygame.square.game.artemis.system.component.RectTransform;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 11.03.2016.
 */
public class BasicTexture extends RGBADrawable {

    private TextureRegion textureRegion;

    public BasicTexture(TextureRegion textureRegion, RectTransform transform) {
        super(transform);
        this.textureRegion = textureRegion;
    }

    @Override
    public void onDraw(PolygonSpriteBatch batch, float offsetX, float offsetY) {
        batch.draw(textureRegion, rectTransform.getX() + offsetX, rectTransform.getY() + offsetY, rectTransform.getWidth() * rectTransform.getWidthScale(), rectTransform.getHeight() * rectTransform.getHeightScale());
    }

    @Override
    void onDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height) {
        float rotationAngle = usesRotation ? getCenteredRotation() : 0;

        batch.draw(textureRegion, rectTransform.getX() + offsetX, rectTransform.getY() + offsetY, width/2, height/2, width, height, 1f, 1f, rotationAngle);
    }
}
