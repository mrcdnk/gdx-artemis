package de.verygame.core.event;

import de.verygame.surface.event.Event;

public class Events {
    public static final Event GAME_OVER = new GameOver();
    public static final Event GAME_PAUSE = new GamePause();
    public static final Event SWITCH_SCREEN = new SwitchScreen();
}
