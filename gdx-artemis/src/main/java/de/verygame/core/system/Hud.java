package de.verygame.core.system;

import de.verygame.square.game.artemis.system.component.behaviour.Behaviour;

/**
 * @author Rico Schrage
 *
 * Describes an api for the square hud.
 */
public interface Hud {

    void callHud(String callString);

    /**
     * Increment the score of the hud score display.
     *
     * @param inc additional score
     */
    void incrementScore(float inc);

    /**
     * Add a power up of the given type. You can provide a duration the power-up will be active.
     *
     * @param powerUpBehaviour event which describes a power-up
     */
    void addPowerUp(Behaviour powerUpBehaviour);

    /**
     * Removes a specified event
     *
     * @param powerUpBehaviour event which descries a power-up
     */
    void removePowerUp(Behaviour powerUpBehaviour);

    /**
     * Resets the Hud to the initial state.
     */
    void reset();

}