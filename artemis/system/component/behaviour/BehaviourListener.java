package de.verygame.square.game.artemis.system.component.behaviour;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 07.04.2016.
 */
public interface BehaviourListener {

    void behaviourStart(Behaviour e);

    void behaviourUpdate(Behaviour e);

    void behaviourEnd(Behaviour e);

}
