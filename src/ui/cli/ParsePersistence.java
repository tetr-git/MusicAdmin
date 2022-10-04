package ui.cli;

import routing.events.LoadEvent;
import routing.events.SaveEvent;
import routing.handler.EventHandler;

public class ParsePersistence implements CliMode {

    EventHandler eventHandler;

    public ParsePersistence(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        if (input.equalsIgnoreCase("Jos Load")){
            new LoadEvent(input,input);
        } else if (input.equalsIgnoreCase("Jos Save")) {
             new SaveEvent(input,input);
        }
    }
}
