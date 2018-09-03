package de.verygame.core.event;

import de.verygame.surface.event.Event;
import de.verygame.surface.event.EventType;

public class SwitchScreen implements Event {
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public EventType getType() {
        return EventType.UI;
    }
}
