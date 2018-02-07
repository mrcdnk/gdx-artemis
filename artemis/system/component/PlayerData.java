package de.verygame.square.game.artemis.system.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Marco Deneke
 *         <p/>
 *         Contains
 *         <p/>
 *         Created by Marco Deneke on 06.01.2016.
 */

@PooledWeaver
public class PlayerData extends Component {

    private boolean shieldActive = false;

    private boolean controlled = true;

    private boolean controlInverted = false;

    public boolean hasShield() {
        return shieldActive;
    }

    public void setShieldActive(boolean shieldActive) {
        this.shieldActive = shieldActive;
    }

    public boolean isControlled() {
        return controlled;
    }

    public void setControlled(boolean controlled) {
        this.controlled = controlled;
    }

    public boolean isControlInverted() {
        return controlInverted;
    }

    public void setControlInverted(boolean controlInverted) {
        this.controlInverted = controlInverted;
    }
}
