package de.verygame.square.game.artemis.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.wrapping.WrappingData;
import de.verygame.square.game.artemis.system.component.wrapping.WrappingState;
import de.verygame.square.game.artemis.system.component.wrapping.WrappingUtils;

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
