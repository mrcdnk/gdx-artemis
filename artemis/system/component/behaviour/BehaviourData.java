package de.verygame.square.game.artemis.system.component.behaviour;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.utils.Array;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 07.04.2016.
 */
@PooledWeaver
public class BehaviourData extends Component {

    private Array<Behaviour> behaviours = new Array<>(3);

    public Array<Behaviour> getBehaviours() {
        return behaviours;
    }

    public void addBehaviour(Behaviour behaviour){
        behaviours.add(behaviour);
    }
}
