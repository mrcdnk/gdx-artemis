package de.verygame.core.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import de.verygame.core.Direction;
import de.verygame.core.system.component.Movement;
import de.verygame.core.system.component.PlayerData;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.control.DirectionListener;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 22.03.2016.
 */
public class ControlSystem extends BaseEntitySystem implements DirectionListener {

    ComponentMapper<PlayerData> playerDataMapper;
    ComponentMapper<RectTransform> rectTransformMapper;
    ComponentMapper<Movement> movementMapper;

    /**
     * Creates a new ControlSystem.
     */
    public ControlSystem() {
        super(Aspect.all(PlayerData.class, RectTransform.class, Movement.class));
    }

    @Override
    protected void initialize(){
        this.setEnabled(false);
    }

    @Override
    protected void processSystem() {
        //no updates, just reacting to swipes
    }

    @Override
    public void onLeft() {
        IntBag entities = subscription.getEntities();

        for (int i = 0; i < entities.size(); i++) {
            updateMovement(movementMapper.get(entities.get(i)), Direction.LEFT);
        }

    }

    @Override
    public void onRight() {
        IntBag entities = subscription.getEntities();

        for (int i = 0; i < entities.size(); i++) {
            updateMovement(movementMapper.get(entities.get(i)), Direction.RIGHT);
        }
    }

    @Override
    public void onUp() {
        IntBag entities = subscription.getEntities();

        for (int i = 0; i < entities.size(); i++) {
            updateMovement(movementMapper.get(entities.get(i)), Direction.UP);
        }
    }

    @Override
    public void onDown() {
        IntBag entities = subscription.getEntities();

        for (int i = 0; i < entities.size(); i++) {
            updateMovement(movementMapper.get(entities.get(i)), Direction.DOWN);
        }
    }

    private void updateMovement(Movement movement, Direction newDirection) {
        Direction current = getDirection(movement);

        if (current == newDirection || current == Direction.NONE) {
            return;
        }

        switch (newDirection) {
            case UP:
                updateYVelocity(movement, current, newDirection);
                break;
            case DOWN:
                updateYVelocity(movement, current, newDirection);
                break;
            case RIGHT:
                updateXVelocity(movement, current, newDirection);
                break;
            case LEFT:
                updateXVelocity(movement, current, newDirection);
                break;

            default:
                //Should never occur

        }

    }

    private void updateYVelocity(Movement movement, Direction current, Direction target) {
        if (current == Direction.DOWN || current == Direction.UP) {
            movement.updateVelocity(0, movement.getYVelocity() * -1);
        }
        else {
            switch (target) {
                case UP:
                    movement.updateVelocity(0, Math.abs(movement.getXVelocity()));
                    break;

                case DOWN:
                    movement.updateVelocity(0, Math.abs(movement.getXVelocity()) * -1);
                    break;

                default:
                    //not possible to happen

            }

        }
    }

    private void updateXVelocity(Movement movement, Direction current, Direction target) {
        if (current == Direction.LEFT || current == Direction.RIGHT) {
            movement.updateVelocity(movement.getXVelocity() * -1, 0);
        }
        else {
            switch (target) {
                case RIGHT:
                    movement.updateVelocity(Math.abs(movement.getYVelocity()), 0);

                    break;

                case LEFT:
                    movement.updateVelocity(Math.abs(movement.getYVelocity()) * -1, 0);

                    break;

                default:
                    //not possible to happen

            }

        }
    }


    public Direction getDirection(Movement movement) {

        //assuming that there are NO diagonal movements for the Player !

        if (movement.getXVelocity() > 0) {
            return Direction.RIGHT;
        }
        else if (movement.getXVelocity() < 0) {
            return Direction.LEFT;
        }
        else if (movement.getYVelocity() > 0) {
            return Direction.UP;
        }
        else if (movement.getYVelocity() < 0) {
            return Direction.DOWN;
        }
        else {
            return Direction.NONE;
        }

    }



}
