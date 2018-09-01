package de.verygame.core.system.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Marco Deneke
 *         <p>
 *         Created by Marco Deneke on 04.01.2016.
 *         <p>
 *         Contains velocity
 */
@PooledWeaver
public class Movement extends Component {

    private float xVelocity = 0;
    private float yVelocity = 0;

    //contain the last translation change
    private float lastXOffset = 0;
    private float lastYOffset = 0;

    private boolean active = true;

    public float getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(float xVelocity) {
        setXVelocity(xVelocity, true);
    }

    public void setXVelocity(float xVelocity, boolean autoScale) {
        if (autoScale) {
            this.xVelocity = xVelocity * Gdx.graphics.getDensity();
        } else {
            this.xVelocity = xVelocity;
        }
    }

    public float getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(float yVelocity) {
        setYVelocity(yVelocity, true);
    }

    public void setYVelocity(float yVelocity, boolean autoScale) {
        if (autoScale) {
            this.yVelocity = yVelocity * Gdx.graphics.getDensity();
        } else {
            this.yVelocity = yVelocity;
        }
    }

    public float getLastXOffset() {
        return lastXOffset;
    }

    public void setLastXOffset(float lastXOffset) {
        this.lastXOffset = lastXOffset;
    }

    public float getLastYOffset() {
        return lastYOffset;
    }

    public void setLastYOffset(float lastYOffset) {
        this.lastYOffset = lastYOffset;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void updateVelocity(float xVelocity, float yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void addVelocity(float velocity) {
        if (MathUtils.isEqual(xVelocity, 0)) {
            this.xVelocity += Math.signum(xVelocity) * velocity * Gdx.graphics.getDensity();
        } else {
            this.yVelocity += Math.signum(yVelocity) * velocity * Gdx.graphics.getDensity();
        }
    }

    public void flipVelocity() {
        this.xVelocity *= -1;
        this.yVelocity *= -1;
    }
}
