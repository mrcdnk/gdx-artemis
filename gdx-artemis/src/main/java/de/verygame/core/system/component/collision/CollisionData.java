package de.verygame.square.game.artemis.system.component.collision;

import com.artemis.Component;
import com.artemis.World;
import com.artemis.annotations.PooledWeaver;

import org.jbox2d.collision.AABB;

import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.artemis.system.component.collision.callback.CollisionCallback;

/**
 * @author Marco Deneke
 *
 * Created by Marco Deneke on 04.01.2016.
 *
 * Contains various data shared by different colliders
 */
@PooledWeaver
public class CollisionData extends Component {

    private AABB aabb = null;

    private boolean active = true;

    private CollisionCallback callback = new CollisionCallback() {
        @Override
        public void collideWith(int eid, World world, EventHandler eventHandler) {

        }
    };

    public AABB getAABB() {
        return aabb;
    }

    public void setAABB(AABB aabb) {
        this.aabb = aabb;
    }

    public CollisionCallback getCollisionCallback() {
        return callback;
    }

    public void setCollisionCallback(CollisionCallback callback) {
        this.callback = callback;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }
}
