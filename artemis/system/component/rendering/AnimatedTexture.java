package de.verygame.square.game.artemis.system.component.rendering;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.util.ArrayUtils;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 11.03.2016.
 */
public class AnimatedTexture extends RGBADrawable {

    private float[] timeSteps;
    private TextureRegion[] textureRegions;
    private World world;

    protected int currentIndex = 0;
    protected float currentElapsed = 0;

    public AnimatedTexture(TextureRegion[] textureRegions, float timestep, RectTransform transform, World artemisWorld) {
        this(textureRegions, ArrayUtils.createAndFill(textureRegions.length, timestep), transform, artemisWorld);
    }

    public AnimatedTexture(TextureRegion[] textureRegions, int startIndex, float timestep, RectTransform transform, World artemisWorld) {
        this(textureRegions, startIndex, ArrayUtils.createAndFill(textureRegions.length, timestep), transform, artemisWorld);
    }

    //timesteps needs to have same size as textureRegions !
    public AnimatedTexture(TextureRegion[] textureRegions, float[] timeSteps, RectTransform transform, World artemisWorld) {
        this(textureRegions, 0, timeSteps, transform, artemisWorld);
    }

    //timesteps needs to have same size as textureRegions !
    public AnimatedTexture(TextureRegion[] textureRegions, int startIndex, float[] timeSteps, RectTransform transform, World artemisWorld) {
        super(transform);
        this.textureRegions = textureRegions;
        this.currentIndex = startIndex;
        this.world = artemisWorld;
        this.timeSteps = timeSteps;
    }

    @Override
    public void onDraw(PolygonSpriteBatch batch, float offsetX, float offsetY) {
        batch.draw(textureRegions[currentIndex], rectTransform.getX() + offsetX, rectTransform.getY() + offsetY,
                rectTransform.getWidth() * rectTransform.getWidthScale(), rectTransform.getHeight() * rectTransform.getHeightScale());

        currentElapsed += world.delta;
        if (currentElapsed >= timeSteps[currentIndex]) {

            currentElapsed -= timeSteps[currentIndex];

            if (currentIndex == textureRegions.length - 1) {
                currentIndex = 0;
            }
            else {
                currentIndex++;
            }
        }
    }

    @Override
    void onDrawCentered(PolygonSpriteBatch batch, float offsetX, float offsetY, float width, float height) {
        batch.draw(textureRegions[currentIndex], rectTransform.getX() + offsetX, rectTransform.getY() + offsetY, width, height);

        currentElapsed += world.delta;
        if (currentElapsed >= timeSteps[currentIndex]) {

            currentElapsed -= timeSteps[currentIndex];

            if (currentIndex == textureRegions.length - 1) {
                currentIndex = 0;
            }
            else {
                currentIndex++;
            }
        }
    }

}
