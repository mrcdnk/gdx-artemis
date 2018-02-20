package de.verygame.square.game.artemis.entities;

import com.artemis.World;

import de.verygame.square.game.resource.GameResourceUnit;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.collision.CollisionData;
import de.verygame.square.game.artemis.system.component.collision.callback.SpeedUpCallback;
import de.verygame.square.game.artemis.system.component.rendering.BasicTexture;
import de.verygame.square.game.artemis.system.component.rendering.RenderData;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 25.02.2017.
 */

public class SpeedUpBuilder extends PowerUpBuilder {


    public SpeedUpBuilder(World world) {
        super(world);
    }

    @Override
    void definePowerUp() {
        getComponent(CollisionData.class).setCollisionCallback(new SpeedUpCallback());

        RenderData rd = getComponent(RenderData.class);
        RectTransform rt = getComponent(RectTransform.class);

        rd.setDrawable(new BasicTexture(resourceHandler.getRegion(GameResourceUnit.POWER_UP_CIRCLE), rt));
        rd.setLayerIndex(4);
        rd.getDrawable().setCenteredDrawable(new BasicTexture(resourceHandler.getRegion(GameResourceUnit.SPEEDO), rt), 0.6f);
    }
}
