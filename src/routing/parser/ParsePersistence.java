package routing.parser;

import routing.events.LoadEvent;
import routing.events.SaveEvent;
import routing.handler.EventHandler;

public class ParsePersistence {

    EventHandler eventHandler;

    public ParsePersistence(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        if (input.equalsIgnoreCase("loadJos")){
            eventHandler.handle(new LoadEvent(input,input));
        } else if (input.equalsIgnoreCase("saveJos")) {
            eventHandler.handle(new SaveEvent(input,input));
        }
    }
}
