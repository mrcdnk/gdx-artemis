package de.verygame.square.game.artemis.system.component.collision.callback;

import com.artemis.World;
import com.badlogic.gdx.Gdx;

import de.verygame.square.core.event.Event;
import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.GameScreens;
import de.verygame.square.game.artemis.system.component.Movement;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.TypeContainer;
import de.verygame.square.util.collision.CollisionUtils;

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
                    eventHandler.emitEvent(Event.SWITCH_SCREEN, GameScreens.GAME_OVER);
                    eventHandler.emitEvent(Event.GAME_PAUSE, true);
                    break;
                case OBSTACLE:
                    loadMappers(world);

                    if (movementMapper.has(eid)) {
                        //both move, detect directions and determine which one flips
                        RectTransform r1 = rectTransformMapper.get(self);
                        RectTransform r2 = rectTransformMapper.get(eid);

                        Movement m2 = movementMapper.get(eid);

                        boolean facesTowards = CollisionUtils.facesTowards(r2.getX(), r2.getY(), r2.getX() + r2.getWidth(true), r2.getY() + r2.getHeight(true), m2.getXVelocity(), m2.getYVelocity(),
                                r1.getX(), r1.getY(), r1.getX() + r1.getWidth(true), r1.getY() + r1.getHeight(true));

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
