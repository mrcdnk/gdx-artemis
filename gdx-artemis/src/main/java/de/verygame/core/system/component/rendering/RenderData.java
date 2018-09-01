package de.verygame.core.system.component.rendering;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 10.03.2016.
 */
public class RenderData extends Component {

    //True when layerIndex is changed at runtime
    private boolean dirty = false;

    private RGBADrawable drawable = null;

    private Array<RGBADrawable> behaviourDrawables = new Array<>(true, 3);

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public int getLayerIndex() {
        return drawable.getLayerIndex();
    }

    public void setLayerIndex(int layerIndex) {
        drawable.setLayerIndex(layerIndex);
    }

    public RGBADrawable getDrawable() {
        return drawable;
    }

    public void setDrawable(RGBADrawable drawable) {
        this.drawable = drawable;
    }

    public void addBehaviourDrawable(RGBADrawable drawable){
        behaviourDrawables.add(drawable);
    }

    public Array<RGBADrawable> getBehaviourDrawables(){
        return behaviourDrawables;
    }

    public void removeBehaviourDrawable(RGBADrawable rgbaDrawable){
        behaviourDrawables.removeValue(rgbaDrawable, true);
    }

}
