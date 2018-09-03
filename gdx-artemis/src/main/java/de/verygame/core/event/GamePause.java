package de.verygame.core.event;

import de.verygame.surface.event.Event;
import de.verygame.surface.event.EventType;

public class GamePause implements Event {
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public EventType getType() {
        return EventType.GAME;
    }
}
