package de.verygame.core.entities;

import com.artemis.World;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.component.rendering.BasicTexture;
import de.verygame.core.system.component.rendering.RenderData;

/**
 * Created by Marco on 23.11.2017.
 *
 * @author Marco Deneke
 */

public class InverterBuilder extends PowerUpBuilder {

    InverterBuilder(World world) {
        super(world);
    }

    @Override
    void definePowerUp() {

        RectTransform rectTransform = getComponent(RectTransform.class);

        RenderData renderData = getComponent(RenderData.class);
        renderData.setDrawable(new BasicTexture(resourceHandler.getRegion(GameResourceUnit.POWER_UP_CIRCLE), rectTransform));
        renderData.setLayerIndex(4);

    }
}
