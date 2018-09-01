package de.verygame.core.system.control;

import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 22.03.2016.
 */
public class GameInputHandler extends GestureDetector {

    private final List<KeyHandler> handlerList;

    public GameInputHandler(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));

        this.handlerList = new ArrayList<>();
    }

    public void registerKeyHandler(KeyHandler handler) {
        handlerList.add(handler);
    }

    public void unregisterKeyHandler(KeyHandler handler) {
        handlerList.remove(handler);
    }

    @Override
    public boolean keyUp(int keycode) {

        boolean upHandled = false;
        for (int i = 0; i < handlerList.size(); ++i) {
            if (handlerList.get(i).keyUp(keycode)) {
                upHandled = true;
            }
        }
        return upHandled;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean downHandled = false;
        for (int i = 0; i < handlerList.size(); ++i) {
            if (handlerList.get(i).keyDown(keycode)) {
                downHandled = true;
            }
        }
        return downHandled;

     }

    @Override
    public boolean keyTyped(char character) {
        boolean typedHandled = false;
        for (int i = 0; i < handlerList.size(); ++i) {
            if (handlerList.get(i).keyTyped(character)) {
                typedHandled= true;
            }
        }
        return typedHandled;
    }

    private static class DirectionGestureListener extends GestureAdapter {
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener) {
            this.directionListener = directionListener;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (velocityX > 0) {
                    directionListener.onRight();
                }
                else {
                    directionListener.onLeft();
                }
            }
            else {
                if (velocityY > 0) {
                    directionListener.onDown();
                }
                else {
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }
}
