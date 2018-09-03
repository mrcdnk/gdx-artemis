package de.verygame.core.entities;

import com.artemis.World;
import de.verygame.core.EntityBuilder;
import de.verygame.core.EntityType;
import de.verygame.core.powerup.PowerUpType;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.component.TypeContainer;
import de.verygame.core.system.component.behaviour.BehaviourData;
import de.verygame.core.system.component.behaviour.SelfDestructBehaviour;
import de.verygame.core.system.component.collision.CircleCollider;
import de.verygame.core.utils.CollisionAdapter;
import de.verygame.core.system.component.collision.CollisionData;
import de.verygame.core.system.component.rendering.RenderData;

import org.jbox2d.collision.AABB;

/**
 * @author Marco Deneke
 *         This class tries to reduce redundancy between the creation of all powerups
 *         Created by Marco Deneke on 25.02.2017.
 */

public abstract class PowerUpBuilder extends EntityBuilder {

    private static final float POWER_UP_TTL = 5f;

    private float x = 0f;
    private float y = 0f;

    PowerUpBuilder(World world) {
        super(world, TypeContainer.class, RectTransform.class, CollisionData.class, CircleCollider.class, RenderData.class, BehaviourData.class);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void define() {
        getComponent(TypeContainer.class).setEntityType(EntityType.POWER_UP);
        RectTransform rt = getComponent(RectTransform.class);
        rt.setWidth(PowerUpType.RADIUS * 2);
        rt.setHeight(PowerUpType.RADIUS * 2);
        rt.setX(x);
        rt.setY(y);

        CircleCollider cc = getComponent(CircleCollider.class);
        cc.setRadius(rt.getWidth() / 2);

        CollisionData cd = getComponent(CollisionData.class);
        cd.setAABB(new AABB(CollisionAdapter.buildLocalAABB(rt.getWidth() * rt.getWidthScale(), rt.getHeight() * rt.getHeightScale())));

        getComponent(BehaviourData.class).addBehaviour(new SelfDestructBehaviour(POWER_UP_TTL));

        definePowerUp();
    }


    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    abstract void definePowerUp();
}
