package de.verygame.core.system.component.wrapping;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.FloatArray;
import de.verygame.core.system.component.RectTransform;

/**
 * Created by Rico on 04.06.2015.
 *
 * StateObjects, which can be mapped to the position of an entity.
 */
public enum WrappingState {

    /**
     * Entity went left out of the screen.
     */
    OUT_LEFT(0, 1) {

        @Override
        protected boolean fromIn() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT, Gdx.graphics.getWidth(), 0);
            return true;
        }

        @Override
        protected boolean fromOutLeftBottom() {
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_LEFT_TOP);
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_TOP);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM, WrappingState.OUT_RIGHT);
            return true;
        }

        @Override
        protected boolean fromOutLeftTop() {
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM);
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_RIGHT_TOP, WrappingState.OUT_RIGHT);
            return true;
        }

    },

    /**
     * Entity went right out of the screen.
     */
    OUT_RIGHT(2, 3) {

        @Override
        protected boolean fromIn() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT, -Gdx.graphics.getWidth(), 0);
            return true;
        }

        @Override
        protected boolean fromOutRightBottom() {
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_LEFT_TOP);
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_TOP);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM, WrappingState.OUT_LEFT);
            return true;
        }

        @Override
        protected boolean fromOutRightTop() {
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM);
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_LEFT_TOP, WrappingState.OUT_LEFT);
            return true;
        }

    },

    /**
     * Entity went top out of the screen.
     */
    OUT_TOP(4, 5) {

        @Override
        protected boolean fromIn() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_BOTTOM, 0, -Gdx.graphics.getHeight());
            return true;
        }

        @Override
        protected boolean fromOutLeftTop() {
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_TOP);
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM, WrappingState.OUT_BOTTOM);
            return true;
        }

    },

    /**
     * Entity went bottom out of the screen.
     */
    OUT_BOTTOM(6, 7) {

        @Override
        protected boolean fromIn() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_TOP, 0, Gdx.graphics.getHeight());
            return true;
        }

        @Override
        protected boolean fromOutLeftBottom() {
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_TOP);
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_LEFT_TOP, WrappingState.OUT_TOP);
            return true;
        }

        @Override
        protected boolean fromOutRightBottom() {
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM);
            WrappingUtils.removeClone(cloneArray, WrappingState.OUT_LEFT_TOP);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_RIGHT_TOP, WrappingState.OUT_TOP);
            return true;
        }

    },

    /**
     * Entity went left-top out of the screen.
     */
    OUT_LEFT_TOP(8, 9) {

        @Override
        protected boolean fromOutTop() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_TOP, Gdx.graphics.getWidth(), 0);
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM, Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_BOTTOM, WrappingState.OUT_LEFT_BOTTOM);
            return true;
        }

        @Override
        protected boolean fromOutLeft() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM, 0, -Gdx.graphics.getHeight());
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM, Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_RIGHT, WrappingState.OUT_RIGHT_TOP);
            return true;
        }

    },

    /**
     * Entity went right-top out of the screen.
     */
    OUT_RIGHT_TOP(10, 11) {

        @Override
        protected boolean fromOutRight() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM, -Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM, 0, -Gdx.graphics.getHeight());
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_LEFT, WrappingState.OUT_LEFT_TOP);
            return true;
        }

        @Override
        protected boolean fromOutTop() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM, -Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_TOP, -Gdx.graphics.getWidth(), 0);
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_BOTTOM, WrappingState.OUT_RIGHT_BOTTOM);
            return true;
        }

    },

    /**
     * Entity went bottom-left out of the screen.
     */
    OUT_LEFT_BOTTOM(12, 13) {

        @Override
        protected boolean fromOutBottom() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_BOTTOM, Gdx.graphics.getWidth(), 0);
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_TOP, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_TOP, WrappingState.OUT_LEFT_TOP);
            return true;
        }

        @Override
        protected boolean fromOutLeft() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_TOP, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_TOP, 0, Gdx.graphics.getHeight());
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_RIGHT, WrappingState.OUT_RIGHT_BOTTOM);
            return true;
        }

    },

    /**
     * Entity went bottom-right out of the screen.
     */
    OUT_RIGHT_BOTTOM(14, 15) {

        @Override
        protected boolean fromOutRight() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_TOP, -Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_RIGHT_TOP, 0, Gdx.graphics.getHeight());
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_LEFT, WrappingState.OUT_LEFT_BOTTOM);
            return true;
        }

        @Override
        protected boolean fromOutBottom() {
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_BOTTOM, -Gdx.graphics.getWidth(), 0);
            WrappingUtils.setClone(cloneArray, WrappingState.OUT_LEFT_TOP, -Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            WrappingUtils.moveClone(cloneArray, WrappingState.OUT_TOP, WrappingState.OUT_RIGHT_TOP);
            return true;
        }

    },

    /**
     * Entity is completely on screen.
     */
    IN(-1, -1) {

        @Override
        protected boolean fromOutLeft() {
            return true;
        }

        @Override
        protected boolean fromOutRight() {
            return true;
        }

        @Override
        protected boolean fromOutTop() {
            return true;
        }

        @Override
        protected boolean fromOutBottom() {
            return true;
        }

        @Override
        protected void postExecute() {
            WrappingUtils.clearClones(cloneArray);
            super.postExecute();
        }

    },

    /**
     * Entity is completely out of the screen.
     */
    OUT(-1, -1) {

        //necessary
        private float xTarget;
        private float yTarget;
        private WrappingState targetState;
        private WrappingState newState;

        //optional
        private WrappingState deleteState = null;
        private WrappingState moveToState = null;
        private WrappingState moveFromState = null;

        @Override
        protected boolean fromOutLeftBottom() {
            if (rectTransform.getX() + rectTransform.getWidth() * rectTransform.getWidthScale() < 0) {
                targetState = WrappingState.OUT_RIGHT_BOTTOM;
                deleteState = WrappingState.OUT_LEFT_TOP;
                newState = WrappingState.OUT_BOTTOM;
                moveToState = WrappingState.OUT_TOP;
            }
            else {
                targetState = WrappingState.OUT_LEFT_TOP;
                deleteState = WrappingState.OUT_RIGHT_BOTTOM;
                newState = WrappingState.OUT_LEFT;
                moveToState = WrappingState.OUT_RIGHT;
            }
            moveFromState = WrappingState.OUT_RIGHT_TOP;
            return true;
        }

        @Override
        protected boolean fromOutRightTop() {
            if (rectTransform.getX() > Gdx.graphics.getWidth()) {
                targetState = WrappingState.OUT_LEFT_TOP;
                deleteState = WrappingState.OUT_RIGHT_BOTTOM;
                newState = WrappingState.OUT_TOP;
                moveToState = WrappingState.OUT_BOTTOM;
            }
            else {
                targetState = WrappingState.OUT_RIGHT_BOTTOM;
                deleteState = WrappingState.OUT_LEFT_TOP;
                newState = WrappingState.OUT_RIGHT;
                moveToState = WrappingState.OUT_LEFT;
            }
            moveFromState = WrappingState.OUT_LEFT_BOTTOM;
            return true;
        }

        @Override
        protected boolean fromOutLeftTop() {
            if (rectTransform.getX() + rectTransform.getWidth() * rectTransform.getWidthScale() < 0) {
                targetState = WrappingState.OUT_RIGHT_TOP;
                deleteState = WrappingState.OUT_LEFT_BOTTOM;
                newState = WrappingState.OUT_TOP;
                moveToState = WrappingState.OUT_BOTTOM;
            }
            else {
                targetState = WrappingState.OUT_LEFT_BOTTOM;
                deleteState = WrappingState.OUT_RIGHT_TOP;
                newState = WrappingState.OUT_LEFT;
                moveToState = WrappingState.OUT_RIGHT;
            }
            moveFromState = WrappingState.OUT_RIGHT_BOTTOM;
            return true;
        }

        @Override
        protected boolean fromOutRightBottom() {
            if (rectTransform.getX() > Gdx.graphics.getWidth()) {
                targetState = WrappingState.OUT_LEFT_BOTTOM;
                deleteState = WrappingState.OUT_RIGHT_TOP;
                newState = WrappingState.OUT_BOTTOM;
                moveToState = WrappingState.OUT_TOP;
            }
            else {
                targetState = WrappingState.OUT_RIGHT_TOP;
                deleteState = WrappingState.OUT_LEFT_BOTTOM;
                newState = WrappingState.OUT_RIGHT;
                moveToState = WrappingState.OUT_LEFT;
            }
            moveFromState = WrappingState.OUT_LEFT_TOP;
            return true;
        }

        @Override
        protected boolean fromOutTop() {
            targetState = WrappingState.OUT_BOTTOM;
            newState = WrappingState.IN;
            return true;
        }

        @Override
        protected boolean fromOutBottom() {
            targetState = WrappingState.OUT_TOP;
            newState = WrappingState.IN;
            return true;
        }

        @Override
        protected boolean fromOutLeft() {
            targetState = WrappingState.OUT_RIGHT;
            newState = WrappingState.IN;
            return true;
        }

        @Override
        protected boolean fromOutRight() {
            targetState = WrappingState.OUT_LEFT;
            newState = WrappingState.IN;
            return true;
        }

        @Override
        protected void postExecute() {
            //execute task with set values
            xTarget = cloneArray.get(targetState.getX());
            yTarget = cloneArray.get(targetState.getY());
            WrappingUtils.removeClone(cloneArray, targetState);
            wrappingData.setWrappingState(newState);

            //execute tasks on optional variables
            if (deleteState != null) {
                WrappingUtils.removeClone(cloneArray, deleteState);
            }
            if (moveFromState != null) {
                WrappingUtils.moveClone(cloneArray, moveFromState, moveToState);
                WrappingUtils.setClone(cloneArray, moveToState, cloneArray.get(moveToState.getX()) - xTarget, cloneArray.get(moveToState.getY()) - yTarget);
            }

            //move entity
            rectTransform.setX(rectTransform.getX() + xTarget);
            rectTransform.setY(rectTransform.getY() + yTarget);
            wrappingData.setDirty(true);
        }

    };

    protected transient WrappingData wrappingData;
    protected transient RectTransform rectTransform;
    protected transient FloatArray cloneArray;

    private final int indexX;
    private final int indexY;

    WrappingState(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public int getX() {
        return indexX;
    }

    public int getY() {
        return indexY;
    }

    protected void preExecute() {
        //default: nothing to do
    }

    public final void execute(RectTransform rectTransform, WrappingData wrappingData) {
        this.wrappingData = wrappingData;
        this.rectTransform = rectTransform;
        this.cloneArray = wrappingData.getClonePositions();

        this.preExecute();
        boolean continueFlag;
        switch (wrappingData.getWrappingState()) {
            case OUT_LEFT:
                continueFlag = fromOutLeft();
                break;

            case OUT_RIGHT:
                continueFlag = fromOutRight();
                break;

            case OUT_TOP:
                continueFlag = fromOutTop();
                break;

            case OUT_BOTTOM:
                continueFlag = fromOutBottom();
                break;

            case OUT_LEFT_TOP:
                continueFlag = fromOutLeftTop();
                break;

            case OUT_RIGHT_TOP:
                continueFlag = fromOutRightTop();
                break;

            case OUT_LEFT_BOTTOM:
                continueFlag = fromOutLeftBottom();
                break;

            case OUT_RIGHT_BOTTOM:
                continueFlag = fromOutRightBottom();
                break;

            case IN:
                continueFlag = fromIn();
                break;

            case OUT:
                continueFlag = fromOut();
                break;

            default:
                continueFlag = false;
                break;
        }
        if (continueFlag) {
            this.postExecute();
        }
    }

    protected boolean fromOutBottom() {
        return false;
    }

    protected boolean fromOutLeftBottom() {
        return false;
    }

    protected boolean fromOutRightTop() {
        return false;
    }

    protected boolean fromOutLeftTop() {
        return false;
    }

    protected boolean fromOutTop() {
        return false;
    }

    protected boolean fromOutRightBottom() {
        return false;
    }

    protected boolean fromOut() {
        return false;
    }

    protected boolean fromIn() {
        return false;
    }

    protected boolean fromOutRight() {
        return false;
    }

    protected boolean fromOutLeft() {
        return false;
    }

    protected void postExecute() {
        wrappingData.setWrappingState(this);
        wrappingData.setDirty(true);
    }

}
