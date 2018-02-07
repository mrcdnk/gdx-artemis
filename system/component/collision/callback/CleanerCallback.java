package de.verygame.square.game.artemis.system.component.collision.callback;

import com.artemis.World;

import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.EntityType;
import de.verygame.square.game.artemis.system.component.TypeContainer;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 09.11.2016.
 */

public class CleanerCallback extends CollisionCallback {

    @Override
    public void collideWith(int eid, World world, EventHandler eventHandler) {
        TypeContainer t = world.getMapper(TypeContainer.class).get(eid);

        if (t.getEntityType() == EntityType.OBSTACLE) {
            world.delete(eid);
        }

    }

}
