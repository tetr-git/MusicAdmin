package routing.parser;

import routing.events.ChangeEvent;
import routing.handler.EventHandler;

public class ParseUpdate {

    EventHandler eventHandler;

    public ParseUpdate(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        eventHandler.handle(new ChangeEvent(input, input));
    }
}
