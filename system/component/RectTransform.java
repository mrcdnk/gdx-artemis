package de.verygame.square.game.artemis.system.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.Gdx;

/**
 * @author Marco Deneke
 *         <p>
 *         Created by Marco Deneke on 04.01.2016.
 *         <p>
 *         Contains information about width, height and rotation.
 */
@PooledWeaver
public class RectTransform extends Component {

    private float x = 0;
    private float y = 0;

    private float width = 100;
    private float height = 100;

    private float widthScale = 1;
    private float heightScale = 1;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getWidth(boolean scaled) {
        if (scaled) {
            return width * widthScale;
        }

        return width;
    }

    public void setWidth(float width) {
        this.width = width * Gdx.graphics.getDensity();
    }

    public void setWidth(float width, boolean autoScale) {
        if (autoScale) {
            this.setWidth(width);
        }
        else {
            this.width = width;
        }
    }

    public float getHeight() {
        return height;
    }

    public float getHeight(boolean scaled) {
        if (scaled) {
            return height * heightScale;
        }

        return height;
    }

    public void setHeight(float height) {
        this.height = height * Gdx.graphics.getDensity();
    }

    public void setHeight(float height, boolean autoScale) {
        if (autoScale) {
            this.setHeight(height);
        }
        else {
            this.height = height;
        }
    }

    public float getWidthScale() {
        return widthScale;
    }

    public void setWidthScale(float widthScale) {
        this.widthScale = widthScale;
    }

    public float getHeightScale() {
        return heightScale;
    }

    public void setHeightScale(float heightScale) {
        this.heightScale = heightScale;
    }

}
