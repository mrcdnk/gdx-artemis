package de.verygame.core.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.component.wrapping.WrappingData;
import de.verygame.core.system.component.wrapping.WrappingState;
import de.verygame.core.system.component.wrapping.WrappingUtils;

/**
 * @author Rico Schrage
 */
public class WrappingSystem extends IteratingSystem {

    protected ComponentMapper<RectTransform> transformMapper;
    protected ComponentMapper<WrappingData> wrappingMapper;

    /**
     * Creates a new IteratingSystem, which will handle the wrapping.
     */
    public WrappingSystem() {
        super(Aspect.all(WrappingData.class, RectTransform.class));
    }

    @Override
    protected void process(int e) {
        RectTransform rect = transformMapper.get(e);
        WrappingData wrappingData = wrappingMapper.get(e);

        WrappingState eps = WrappingUtils.determineEntityState(rect);
        eps.execute(rect, wrappingData);
    }
}
