package de.verygame.core.system.component.collision.callback;

import com.artemis.World;
import de.verygame.core.EntityType;
import de.verygame.core.system.component.TypeContainer;

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

