package de.verygame.square.game.artemis.system.component.collision.callback;

import com.artemis.World;
import com.badlogic.gdx.Gdx;

import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.artemis.system.component.behaviour.BehaviourData;
import de.verygame.square.game.artemis.system.component.behaviour.PowerUp.TempSpeedUpBehaviour;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 05.06.2017.
 */

public class TempSpeedUpCallback extends PowerUpCallback {

    private static final float DURATION = 3f;
    private static final float SPEED_MOD = 20f* Gdx.graphics.getDensity();

    @Override
    void modifyPlayer(int eid, World world, EventHandler eventHandler) {
        BehaviourData behaviourData = world.getMapper(BehaviourData.class).get(eid);
        behaviourData.addBehaviour(new TempSpeedUpBehaviour(DURATION, SPEED_MOD));
    }
}
