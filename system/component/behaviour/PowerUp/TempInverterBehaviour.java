package de.verygame.square.game.artemis.system.component.behaviour.PowerUp;

import com.artemis.World;

import de.verygame.square.game.artemis.system.component.behaviour.TimedBehaviour;
import de.verygame.square.game.artemis.system.component.PlayerData;

/**
 * Created by Marco on 06.12.2017.
 *
 * @author Marco Deneke
 */

public class TempInverterBehaviour extends TimedBehaviour {

    private World artemisWorld;
    private int entity;

    private PlayerData pd;

    public TempInverterBehaviour(float processTime) {
        super(processTime);
    }

    @Override
    public void onStart() {
        pd = artemisWorld.getMapper(PlayerData.class).get(entity);
        pd.setControlInverted(!pd.isControlInverted());
    }

    @Override
    public void process(float deltaTime) {
        //nothing to do here
    }

    @Override
    public void onEnd() {
        pd.setControlInverted(!pd.isControlInverted());
    }

    @Override
    public BehaviourType getBehaviourType() {
        return BehaviourType.POWER_UP;
    }
}
