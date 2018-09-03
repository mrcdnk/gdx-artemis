package de.verygame.core.system.component.collision.callback;

import com.artemis.World;
import de.verygame.core.EntityType;
import de.verygame.core.system.component.TypeContainer;
import de.verygame.core.system.component.behaviour.BehaviourData;
import de.verygame.core.system.component.behaviour.SelfDestructBehaviour;
import de.verygame.core.system.component.collision.CollisionData;
import de.verygame.surface.event.EventHandler;

/**
 * Created by Marco on 19.01.2017.
 *
 * @author Marco Deneke
 */

public abstract class PowerUpCallback extends CollisionCallback {


    @Override
    public void collideWith(int eid, World world, EventHandler eventHandler) {
        TypeContainer typeContainer = world.getMapper(TypeContainer.class).get(eid);

        if (typeContainer != null && typeContainer.getEntityType() == EntityType.SQUARE) {
            modifyPlayer(eid, world, eventHandler);

            //disable collision to avoid being applied multiple times
            world.getMapper(CollisionData.class).get(self).setActive(false);
            world.getMapper(BehaviourData.class).get(self).addBehaviour(new SelfDestructBehaviour(0f));
        }
    }

    abstract void modifyPlayer(int eid, World world, EventHandler eventHandler);
}
