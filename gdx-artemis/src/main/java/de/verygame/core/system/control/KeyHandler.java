package de.verygame.core.system.control;

/**
 * @author Rico Schrage
 */
public interface KeyHandler {

    boolean keyDown(int keyCode);
    boolean keyUp(int keyCode);
    boolean keyTyped(char c);

}
