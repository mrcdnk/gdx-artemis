package de.verygame.square.game.artemis.system.component.wrapping;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.utils.FloatArray;

import de.verygame.square.util.ArrayUtils;

/**
 * @author Rico Schrage
 *
 * Contains information about the state regarding the wrapping of the entity.
 */
@PooledWeaver
public class WrappingData extends Component {

    /** Indicates that the clone does not exist */
    public static final float NO_CLONE = Float.POSITIVE_INFINITY;

    /** Fixed size of the clone array */
    public static final int FIXED_ARRAY_SIZE = 16;

    /** Contains the positions of all wrapping clones. (X_OUTLEFT, Y_OUTLEFT, X_OUTRIGHT, Y_OUTRIGHT, usw.) */
    private FloatArray clonePositions = new FloatArray(ArrayUtils.createAndFill(FIXED_ARRAY_SIZE, Float.POSITIVE_INFINITY));

    /** State of the entity regarding the wrapping */
    private WrappingState wrappingState = WrappingState.IN;

    private boolean dirty = true;

    /**
     * @return array of clone positions
     */
    public FloatArray getClonePositions() {
        return clonePositions;
    }

    /**
     * @return state of the wrapping progress
     */
    public WrappingState getWrappingState() {
        return wrappingState;
    }

    /**
     * Set the state of the wrapping progress
     *
     * @param state state of the wrapping
     */
    public void setWrappingState(WrappingState state) {
        this.wrappingState = state;
    }

    /**
     * @return true if this wrapping data is dirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Set the dirty flag.
     *
     * @param dirty indicates whether this data has been changed (and not processed yet) recently
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

}
