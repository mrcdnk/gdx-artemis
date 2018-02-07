package de.verygame.square.game.artemis.system.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

import de.verygame.square.game.EntityType;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 09.05.2016.
 */
@PooledWeaver
public class TypeContainer extends Component {

    private EntityType entityType = EntityType.NONE;

    private boolean initialize = false;

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public boolean isInitialize() {
        return initialize;
    }

    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }
}
