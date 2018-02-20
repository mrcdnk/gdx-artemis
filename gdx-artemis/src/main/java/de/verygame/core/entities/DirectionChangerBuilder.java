package de.verygame.square.game.artemis.entities;

import com.artemis.World;

import de.verygame.square.game.resource.GameResourceUnit;
import de.verygame.square.game.artemis.system.component.CenteredDrawableRotationBehaviour;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.behaviour.BehaviourData;
import de.verygame.square.game.artemis.system.component.collision.CollisionData;
import de.verygame.square.game.artemis.system.component.collision.callback.DirectionChangerCallback;
import de.verygame.square.game.artemis.system.component.rendering.BasicTexture;
import de.verygame.square.game.artemis.system.component.rendering.RenderData;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 09.06.2017.
 */

public class DirectionChangerBuilder extends PowerUpBuilder {

    public DirectionChangerBuilder(World world) {
        super(world);
    }

    @Override
    void definePowerUp() {
        RenderData rd = getComponent(RenderData.class);
        RectTransform rt = getComponent(RectTransform.class);
        BehaviourData bd = getComponent(BehaviourData.class);

        rd.setDrawable(new BasicTexture(resourceHandler.getRegion(GameResourceUnit.POWER_UP_CIRCLE), rt));
        rd.setLayerIndex(4);
        rd.getDrawable().setCenteredDrawable(new BasicTexture(resourceHandler.getRegion(GameResourceUnit.ARROW), rt), 0.5f, true);

        CenteredDrawableRotationBehaviour rotationBehaviour = new CenteredDrawableRotationBehaviour(90, 1);
        bd.addBehaviour(rotationBehaviour);

        getComponent(CollisionData.class).setCollisionCallback(new DirectionChangerCallback(rotationBehaviour));

    }
}
