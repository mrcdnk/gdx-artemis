package de.verygame.square.game.artemis.system.component.behaviour;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 07.04.2016.
 */
public interface Behaviour {

    /**
     * Initializing before update is called
     */
    void onStart();

    /**
     * Updates the state of the event, called by the EventSystem
     * @param deltaTime delta process time
     */
    void update(float deltaTime);

    /**
     * Manipulate components based on current State
     *
     * @param deltaTime delta process time
     */
    void process(float deltaTime);

    /**
     * Anything that needs to be cleaned up after finishing the event
     */
    void onEnd();

    BehaviourState getCurrentState();

    BehaviourType getBehaviourType();

    enum BehaviourState {
        INITIALIZE, PROCESS, FINISH, IDLE;
    }

    enum BehaviourType {
        DEFAULT, FADE_SPAWN, POWER_UP, SELF_DESTRUCT, CENTERED_DRAWABLE_ROTATION;
    }
}
