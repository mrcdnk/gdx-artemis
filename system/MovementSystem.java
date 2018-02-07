package de.verygame.square.game.artemis.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;

import de.verygame.square.game.artemis.system.component.Movement;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.collision.CollisionData;
import de.verygame.square.game.system.component.collision.*;

/**
 * @author Marco Deneke
 *         <p>
 *         Created by Marco Deneke on 04.01.2016.
 *         <p>
 *         Proccesses all movements of entitys.
 */
@Wire(failOnNull = false)
public class MovementSystem extends IteratingSystem {

    ComponentMapper<RectTransform> transformMapper;
    ComponentMapper<Movement> movementMapper;
    ComponentMapper<CollisionData> collisionDataMapper;

    CollisionSystem collisionSystem;

    /**
     * Creates a new EntityProcessingSystem.
     * Handles all movement in x/y directions
     */
    public MovementSystem() {
        super(Aspect.all(RectTransform.class, Movement.class));
    }

    @Override
    protected void process(int e) {
        //Get mapped components
        RectTransform t = transformMapper.get(e);
        Movement m = movementMapper.get(e);
        CollisionData c = collisionDataMapper.get(e);

        if(m.isActive()) {
            float xT = m.getXVelocity() * world.delta;
            float yT = m.getYVelocity() * world.delta;

            //process movement
            t.setX(t.getX() + xT);
            t.setY(t.getY() + yT);

            m.setLastXOffset(xT);
            m.setLastYOffset(yT);

            if (c != null && collisionSystem != null) {
                collisionSystem.move(e, xT, yT);
            }
        }
    }
}
