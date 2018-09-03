package de.verygame.core.system.component.collision.callback;

import com.artemis.World;
import com.badlogic.gdx.math.MathUtils;
import de.verygame.core.system.ControlSystem;
import de.verygame.core.system.component.CenteredDrawableRotationBehaviour;
import de.verygame.surface.event.EventHandler;


/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 09.06.2017.
 */

public class DirectionChangerCallback extends PowerUpCallback {

    private CenteredDrawableRotationBehaviour rotationBehaviour;

    public DirectionChangerCallback(CenteredDrawableRotationBehaviour rotationBehaviour){
        this.rotationBehaviour = rotationBehaviour;
    }

    @Override
    void modifyPlayer(int eid, World world, EventHandler eventHandler) {
        ControlSystem system = world.getSystem(ControlSystem.class);
        if(MathUtils.isEqual(rotationBehaviour.getCurrentRotation()-90f, 0)){
            system.onUp();
        } else if(MathUtils.isEqual(rotationBehaviour.getCurrentRotation()-90, 90)){
            system.onLeft();
        }else if(MathUtils.isEqual(rotationBehaviour.getCurrentRotation()-90, 180)){
            system.onDown();
        }else {
            system.onRight();
        }
    }
}
