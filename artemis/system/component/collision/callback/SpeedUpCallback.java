package de.verygame.square.game.artemis.system.component.collision.callback;

import com.artemis.ComponentMapper;
import com.artemis.World;

import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.artemis.system.component.Movement;

/**
 * Created by Marco on 19.01.2017.
 *
 * @author Marco Deneke
 */

public class SpeedUpCallback extends PowerUpCallback {

    private static final float SPEED_MOD = 15f;

    @Override
    void modifyPlayer(int eid, World world, EventHandler eventHandler) {
        ComponentMapper<Movement> movementMapper = world.getMapper(Movement.class);

        Movement m = movementMapper.get(eid);

        m.addVelocity(SPEED_MOD);
    }
}
