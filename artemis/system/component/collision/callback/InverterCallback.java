package de.verygame.square.game.artemis.system.component.collision.callback;

import com.artemis.World;

import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.artemis.system.component.behaviour.BehaviourData;
import de.verygame.square.game.artemis.system.component.behaviour.PowerUp.TempInverterBehaviour;

/**
 * Created by Marco on 06.12.2017.
 *
 * @author Marco Deneke
 */

public class InverterCallback extends PowerUpCallback {

    private static final float DURATION = 3f;

    @Override
    void modifyPlayer(int eid, World world, EventHandler eventHandler) {

        BehaviourData behaviourData = world.getMapper(BehaviourData.class).get(eid);
        behaviourData.addBehaviour(new TempInverterBehaviour(DURATION));

    }
}
