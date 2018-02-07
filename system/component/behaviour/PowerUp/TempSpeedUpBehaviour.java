package de.verygame.square.game.artemis.system.component.behaviour.PowerUp;

import com.artemis.World;

import de.verygame.square.game.artemis.system.component.Movement;
import de.verygame.square.game.artemis.system.component.behaviour.TimedBehaviour;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 05.06.2017.
 */

public class TempSpeedUpBehaviour extends TimedBehaviour {

    private World artemisWorld;
    private int entity;

    private Movement movement;
    private float speedOffset;

    public TempSpeedUpBehaviour(float processTime, float speedOffset) {
        super(processTime);
        this.speedOffset = speedOffset;
    }

    @Override
    public void onStart() {
        movement = artemisWorld.getMapper(Movement.class).get(entity);
        movement.addVelocity(speedOffset);
    }

    @Override
    public void process(float deltaTime) {
        //Nothing to do here
    }

    @Override
    public void onEnd() {
        movement.addVelocity(-speedOffset);
    }

    @Override
    public BehaviourType getBehaviourType() {
        return BehaviourType.POWER_UP;
    }
}
