package de.verygame.core.system.component.collision.callback;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import de.verygame.core.event.Events;
import de.verygame.core.system.component.Movement;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.component.TypeContainer;
import de.verygame.core.utils.CollisionUtils;
import de.verygame.surface.event.EventHandler;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 17.05.2016.
 */
public class ObstacleCallback extends CollisionCallback {

    @Override
    public void collideWith(int eid, World world, EventHandler eventHandler) {

        TypeContainer typeContainer = world.getMapper(TypeContainer.class).get(eid);

        if (typeContainer != null) {

            switch (typeContainer.getEntityType()) {
                case SQUARE:
                    eventHandler.emitEvent(Events.SWITCH_SCREEN, Events.GAME_OVER);
                    eventHandler.emitEvent(Events.GAME_PAUSE, true);
                    break;
                case OBSTACLE:
                    loadMappers(world);

                    if (movementMapper.has(eid)) {
                        //both move, detect directions and determine which one flips
                        RectTransform r1 = rectTransformMapper.get(self);
                        RectTransform r2 = rectTransformMapper.get(eid);

                        Movement m2 = movementMapper.get(eid);

                        boolean facesTowards = CollisionUtils.facesTowards(r2, m2, r1);

                        System.out.println(facesTowards);

                        if (facesTowards) {
                            m2.flipVelocity();
                            r2.setX(r2.getX() + (-1 * m2.getLastXOffset()) + m2.getXVelocity() * Gdx.graphics.getDeltaTime());
                            r2.setY(r2.getY() + (-1 * m2.getLastYOffset()) + m2.getYVelocity() * Gdx.graphics.getDeltaTime());
                        }
                    }
                    break;
                default:
                    //do nothing
            }

        }
    }


}
