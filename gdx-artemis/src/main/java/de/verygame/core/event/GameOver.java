package de.verygame.core.event;

import de.verygame.surface.event.Event;
import de.verygame.surface.event.EventType;

public class GameOver implements Event {
    @Override
    public int getId() {
        return 3;
    }

    @Override
    public EventType getType() {
        return EventType.GAME;
    }
}
