package de.verygame.core.system.component.collision.callback;

import com.artemis.ComponentMapper;
import com.artemis.World;
import de.verygame.core.system.component.Movement;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.component.collision.CollisionData;

/**
 * @author Marco Deneke
 *         <p>
 *         Created by Marco Deneke on 19.02.2016.
 */
public abstract class CollisionCallback {

    protected int self;

    protected ComponentMapper<CollisionData> collisionDataMapper;
    protected ComponentMapper<RectTransform> rectTransformMapper;
    protected ComponentMapper<Movement> movementMapper;

    public abstract void collideWith(int eid, World world, EventHandler eventHandler);

    public void loadMappers(World world){
        collisionDataMapper = world.getMapper(CollisionData.class);
        rectTransformMapper = world.getMapper(RectTransform.class);
        movementMapper = world.getMapper(Movement.class);
    }

    public void setSelf(int self) {
        this.self = self;
    }

    public int getSelf() {
        return self;
    }
}
