package ui.cli;


import routing.events.DeleteEvent;
import routing.handler.EventHandler;

public class ParseDelete {

    EventHandler eventHandler;

    public ParseDelete(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        eventHandler.handle(new DeleteEvent(input,input));
    }
}
