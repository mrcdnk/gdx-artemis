package de.verygame.square.game.artemis.system.component.wrapping;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.FloatArray;

import java.util.Arrays;

import de.verygame.square.game.artemis.system.component.RectTransform;

/**
 * @author Rico Schrage
 *
 * Contains various utility methods to handle wrapping.
 */
public class WrappingUtils {

    private WrappingUtils() {
        //utility class
    }

    /**
     * Clears all clones in the given array.
     *
     * @param array clone array
     */
    public static void clearClones(FloatArray array) {
        Arrays.fill(array.items, WrappingData.NO_CLONE);
    }

    /**
     * Calls setClone with xValue, yValue = WrappingData.NO_CLONE
     *
     * @param array clone array
     * @param state state of the clone
     */
    public static void removeClone(FloatArray array, WrappingState state) {
        setClone(array, state, WrappingData.NO_CLONE, WrappingData.NO_CLONE);
    }

    /**
     * Set the given x and yValues to the clone with the given state in the array.
     *
     * @param array clone array
     * @param state state of the clone to identify the clone
     * @param xValue new x value, use Float.POSITIVE_INFINITY to indicate that the clone does not exist.
     * @param yValue new y value, use Float.POSITIVE_INFINITY to indicate that the clone does not exist.
     */
    public static void setClone(FloatArray array, WrappingState state, float xValue, float yValue) {
        array.set(state.getX(), xValue);
        array.set(state.getY(), yValue);
    }

    /**
     * Moves the clone at the given state to a specified new state.
     *
     * @param array clone array
     * @param oldState old state of the clone
     * @param newState new state of the clone
     */
    public static void moveClone(FloatArray array, WrappingState oldState, WrappingState newState) {
        array.set(newState.getX(), array.get(oldState.getX()));
        array.set(newState.getY(), array.get(oldState.getY()));
        removeClone(array, oldState);
    }

    /**
     * Method to check where (and if) the entity is out of the screen.
     *
     * @param rect rect
     * @return enum which indicates the position of an entity
     */
    public static WrappingState determineEntityState(RectTransform rect) {

        float gameWidth = Gdx.graphics.getWidth();
        float gameHeight = Gdx.graphics.getHeight();

        float widthScaled = rect.getWidth() * rect.getWidthScale();
        float heightScaled = rect.getHeight() * rect.getHeightScale();

        float x = rect.getX();
        float y = rect.getY();

        if (rect.getX() >= gameWidth || rect.getX() + widthScaled < 0 || y >= gameHeight || y + heightScaled < 0) {
            return WrappingState.OUT;
        }

        //checking if entity crossed left, right, top, bottom border.
        if (x + widthScaled > gameWidth) {
            //checking if right-top, right-bottom or right
            if (y + heightScaled > gameHeight) {
                return WrappingState.OUT_RIGHT_TOP;
            }
            else if (y < 0) {
                return WrappingState.OUT_RIGHT_BOTTOM;
            }
            return WrappingState.OUT_RIGHT;
        }
        else if (x < 0) {
            //checking if left-top, left-bottom ot left
            if (y + heightScaled > gameHeight) {
                return WrappingState.OUT_LEFT_TOP;
            }
            else if (y < 0) {
                return WrappingState.OUT_LEFT_BOTTOM;
            }
            return WrappingState.OUT_LEFT;
        }
        else if (y + heightScaled > gameHeight) {
            return WrappingState.OUT_TOP;
        }
        else if (y < 0) {
            return WrappingState.OUT_BOTTOM;
        }

        //if this part is reached entity if obv. completely on screen
        return WrappingState.IN;
    }

}
