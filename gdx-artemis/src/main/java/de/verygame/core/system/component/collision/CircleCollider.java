package de.verygame.square.game.artemis.system.component.collision;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Marco Deneke
 *         <p/>
 *         Created by Marco Deneke on 04.01.2016.
 *         <p/>
 *         Contains the radius of a circular collider
 */
@PooledWeaver
public class CircleCollider extends Component {

    private float radius = 0;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
