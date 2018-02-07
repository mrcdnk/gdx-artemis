package de.verygame.square.game.artemis.system.component.behaviour;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.math.ConvexHull;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.game.artemis.system.component.Movement;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.collision.CollisionData;
import de.verygame.square.game.artemis.system.component.rendering.PolygonTexture;
import de.verygame.square.game.artemis.system.component.rendering.RenderData;
import de.verygame.square.game.artemis.system.component.rendering.RGBADrawable;
import de.verygame.square.resource.CommonResourceUnit;
import de.verygame.square.util.ArrayUtils;
import de.verygame.square.util.PolygonUtils;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 11.05.2016.
 */
public class FadeSpawn extends TimedBehaviour {

    private static final float ALPHA_BASE_VALUE = 0.3f;
    private static final float BORDER_WIDTH = 10 * Gdx.graphics.getDensity();
    private FloatArray convexHull;
    private final Vector2[] polygon;
    private FloatArray borderPoly;
    private PolygonRegion borderRegion;
    private RectTransform rectTransform;
    private static final ConvexHull convexHullProcessor = new ConvexHull();


    private World artemisWorld;
    private int entity;
    private ResourceHandler resourceHandler;

    private ComponentMapper<RectTransform> rectTransformMapper;
    private ComponentMapper<RenderData> renderDataMapper;
    private ComponentMapper<CollisionData> collisionDataMapper;
    private ComponentMapper<Movement> movementMapper;
    private RGBADrawable drawable;
    private RenderData data;

    private PolygonTexture polygonTexture;

    public FadeSpawn(float timeUntilSpawn) {
        super(timeUntilSpawn);
        this.polygon = new Vector2[0];
    }

    public FadeSpawn(float timeUntilSpawn, Vector2[] polygon) {
        super(timeUntilSpawn);
        this.polygon = polygon;
    }

    @Override
    public void onStart() {
        rectTransformMapper = artemisWorld.getMapper(RectTransform.class);
        renderDataMapper = artemisWorld.getMapper(RenderData.class);
        collisionDataMapper = artemisWorld.getMapper(CollisionData.class);
        movementMapper = artemisWorld.getMapper(Movement.class);

        rectTransform = rectTransformMapper.get(entity);

        data = renderDataMapper.get(entity);

        drawable = data.getDrawable();
        drawable.setAlpha(ALPHA_BASE_VALUE);

        convexHull = convexHullProcessor.computePolygon(ArrayUtils.buildVertexArray(polygon), false);

        ArrayUtils.removeDuplicates(convexHull);

        borderPoly = PolygonUtils.createBorderPolygon(convexHull, BORDER_WIDTH, rectTransform.getWidth() * rectTransform.getWidthScale(), rectTransform.getHeight() * rectTransform.getHeightScale());

        polygonTexture = new PolygonTexture(resourceHandler.getRegion(CommonResourceUnit.FLAT_BACKGROUND), rectTransform, borderPoly.toArray());

        data.addBehaviourDrawable(polygonTexture);

        collisionDataMapper.get(entity).setActive(false);

        if (movementMapper.has(entity)) {
            movementMapper.get(entity).setActive(false);
        }
    }

    @Override
    public void process(float deltaTime) {
        data.removeBehaviourDrawable(polygonTexture);
        data.addBehaviourDrawable(new PolygonTexture(resourceHandler.getRegion(CommonResourceUnit.FLAT_BACKGROUND), rectTransform, PolygonUtils.calculatePartialBorderPolygon(borderPoly, getProgress()).toArray()));
    }

    @Override
    public void onEnd() {
        drawable.setAlpha(1f);
        drawable.removeCenteredDrawable();
        renderDataMapper.get(entity).removeBehaviourDrawable(polygonTexture);

        collisionDataMapper.get(entity).setActive(true);

        if (movementMapper.has(entity)) {
            movementMapper.get(entity).setActive(true);
        }
    }

    @Override
    public BehaviourType getBehaviourType() {
        return BehaviourType.FADE_SPAWN;
    }
}
