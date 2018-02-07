package de.verygame.square.game.artemis.system.component;

import com.artemis.World;

import de.verygame.square.game.artemis.system.component.behaviour.Behaviour;
import de.verygame.square.game.artemis.system.component.rendering.Drawable;
import de.verygame.square.game.artemis.system.component.rendering.RenderData;

import static de.verygame.square.game.artemis.system.component.behaviour.Behaviour.BehaviourState.PROCESS;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 10.06.2017.
 */

public class CenteredDrawableRotationBehaviour implements Behaviour {

    private float currentRotation;
    private float currentElapsed;
    private BehaviourState currentState = BehaviourState.INITIALIZE;

    private Drawable drawable;
    private float rotationStep;
    private float timeStep;

    private World artemisWorld;
    private int entity;

    private static final float FULL_ROTATION = 360;
    /**
     * Behaviour which rotates the centeredDrawable of any drawable counter clockwise in given timeSteps and rotationSteps
     * @param rotationStep rotation per step in degree
     * @param timeStep time between two steps in seconds
     */
    public CenteredDrawableRotationBehaviour(float rotationStep, float timeStep){
        this.rotationStep = rotationStep;
        this.timeStep = timeStep;
    }

    @Override
    public void onStart() {
        drawable = artemisWorld.getMapper(RenderData.class).get(entity).getDrawable();
    }

    @Override
    public void update(float deltaTime) {

        switch (currentState) {
            case INITIALIZE:
                onStart();
                this.currentState = PROCESS;
            case PROCESS:
                process(deltaTime);
                break;
            default:
                //do nothing
        }

    }

    @Override
    public void process(float deltaTime) {
        currentElapsed += deltaTime;

        if (currentElapsed >= timeStep) {
            currentRotation += rotationStep;
            currentElapsed -= timeStep;

            if (currentRotation >= FULL_ROTATION) {
                currentRotation -= FULL_ROTATION;
            }
        }

        drawable.setCenteredRotation(currentRotation - 90f);
    }

    @Override
    public void onEnd() {
        //nothing to do always in BehaviourState.PROCESSs
    }

    @Override
    public BehaviourState getCurrentState() {
        return currentState;
    }

    @Override
    public BehaviourType getBehaviourType() {
        return BehaviourType.CENTERED_DRAWABLE_ROTATION;
    }

    public float getCurrentRotation() {
        return currentRotation;
    }
}
