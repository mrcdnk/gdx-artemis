package de.verygame.core.system.component.behaviour;

import com.artemis.World;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 25.02.2017.
 */

public class SelfDestructBehaviour extends TimedBehaviour {

    private World artemisWorld;

    private int entity;

    public SelfDestructBehaviour(float timeToDeath){
        super(timeToDeath);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void process(float deltaTime) {

    }

    @Override
    public void onEnd() {
        artemisWorld.delete(entity);
    }

    @Override
    public BehaviourType getBehaviourType() {
        return null;
    }
}
