package de.verygame.square.game.artemis.system.control;

/**
 * @author Rico Schrage
 *
 * Conveniecne class which contains dummy implementations for all methods of the interface {@link KeyHandler}.
 */
public class KeyHandlerAdapter implements KeyHandler {

    @Override
    public boolean keyDown(int keyCode) {
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

}
