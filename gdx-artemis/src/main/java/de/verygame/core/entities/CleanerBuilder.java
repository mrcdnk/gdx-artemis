package de.verygame.core.entities;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import de.verygame.core.Direction;
import de.verygame.core.EntityBuilder;

import de.verygame.core.EntityType;
import de.verygame.core.resource.CommonResourceUnit;
import de.verygame.core.system.component.Movement;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.component.TypeContainer;
import de.verygame.core.system.component.behaviour.BehaviourData;
import de.verygame.core.system.component.behaviour.FadeSpawn;
import de.verygame.core.system.component.collision.BoxCollider;
import de.verygame.core.system.component.collision.CollisionData;
import de.verygame.core.system.component.collision.callback.CleanerCallback;
import de.verygame.core.system.component.rendering.BasicTexture;
import de.verygame.core.system.component.rendering.RenderData;
import de.verygame.core.utils.PolygonUtils;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 24.02.2017.
 */

public class CleanerBuilder extends EntityBuilder {
    private static final float HEIGHT = 0.075f;

    private final float clearTime;
    private final float fadeTime;
    private Direction direction;

    public CleanerBuilder(World world, float clearTime, float fadeTime) {
        super(world, TypeContainer.class, RectTransform.class, Movement.class, BoxCollider.class, CollisionData.class, RenderData.class, BehaviourData.class);
        this.clearTime = clearTime;
        this.fadeTime = fadeTime;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    protected void define() {
        TypeContainer typeContainer = getComponent(TypeContainer.class);
        typeContainer.setEntityType(EntityType.CLEANER);

        Movement m = getComponent(Movement.class);
        RectTransform rectTransform = getComponent(RectTransform.class);
        RenderData rd = getComponent(RenderData.class);

        switch (direction) {
            case DOWN:
                m.setYVelocity(-(Gdx.graphics.getHeight() / clearTime) / Gdx.graphics.getDensity());
                rectTransform.setX(0);
                rectTransform.setY(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() * HEIGHT);
                rectTransform.setWidth(Gdx.graphics.getWidth(), false);
                rectTransform.setHeight(Gdx.graphics.getHeight() * HEIGHT, false);
                break;
            case UP:
                m.setYVelocity((Gdx.graphics.getHeight() / clearTime) / Gdx.graphics.getDensity());
                rectTransform.setX(0);
                rectTransform.setY(0);
                rectTransform.setWidth(Gdx.graphics.getWidth(), false);
                rectTransform.setHeight(Gdx.graphics.getHeight() * HEIGHT, false);
                break;
            case LEFT:
                m.setXVelocity(-(Gdx.graphics.getWidth() / clearTime) / Gdx.graphics.getDensity());
                rectTransform.setX(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * HEIGHT);
                rectTransform.setY(0);
                rectTransform.setWidth(Gdx.graphics.getWidth() * HEIGHT, false);
                rectTransform.setHeight(Gdx.graphics.getHeight(), false);
                break;
            case RIGHT:
                m.setXVelocity((Gdx.graphics.getWidth() / clearTime) / Gdx.graphics.getDensity());
                rectTransform.setX(0);
                rectTransform.setY(0);
                rectTransform.setWidth(Gdx.graphics.getWidth() * HEIGHT, false);
                rectTransform.setHeight(Gdx.graphics.getHeight(), false);
                break;
             default:
                 //should not happen
        }


        rd.setDrawable(new BasicTexture(resourceHandler.getRegion(CommonResourceUnit.FLAT_BACKGROUND), rectTransform));

        rd.setLayerIndex(5);

        BoxCollider b = getComponent(BoxCollider.class);

        b.setHeight(rectTransform.getHeight());
        b.setWidth(rectTransform.getWidth());

        CollisionData cd = getComponent(CollisionData.class);

        cd.setAABB(new AABB(new Vec2(0f, 0f), new Vec2(rectTransform.getWidth(), rectTransform.getHeight())));

        cd.setCollisionCallback(new CleanerCallback());

        getComponent(BehaviourData.class).addBehaviour(new FadeSpawn(fadeTime, PolygonUtils.constructRectanglePolygon(3, 3)));

    }
}
