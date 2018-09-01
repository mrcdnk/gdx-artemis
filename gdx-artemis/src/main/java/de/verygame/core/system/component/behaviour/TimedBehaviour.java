package de.verygame.core.system.component.behaviour;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 21.04.2016.
 */
public abstract class TimedBehaviour implements Behaviour {

    private float timer = 0;
    private final float processTime;

    private BehaviourState currentState = BehaviourState.INITIALIZE;


    public TimedBehaviour(float processTime) {
        this.processTime = processTime;
    }

    @Override
    public void update(float deltaTime) {
        switch (currentState) {
            case INITIALIZE:
                onStart();
                this.currentState = BehaviourState.PROCESS;

            case PROCESS:
                if (timeStep(deltaTime)) {
                    break;
                }

            case FINISH:
                onEnd();
                this.currentState = BehaviourState.IDLE;
                break;
            case IDLE:
                //Wait until self destruction
                break;
            default:
                //Unsupported State
                break;
        }
    }

    private boolean timeStep(float deltaTime) {
        timer += deltaTime;

        if (processTime - timer > 0) {
            process(deltaTime);
            return true;
        } else {
            process(deltaTime + processTime - timer);
            this.currentState = BehaviourState.FINISH;
            return false;
        }
    }

    /**
     * @return The progress between 0 (no progress) and 1 done.
     */
    public float getProgress(){
        return timer/processTime;
    }

    @Override
    public BehaviourState getCurrentState() {
        return currentState;
    }

}
