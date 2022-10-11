package routing.parser;

import routing.RepoCollection;
import routing.events.InstanceChangeEvent;
import routing.handler.EventHandler;

public class ParseStorage {

    EventHandler eventHandler;

    public ParseStorage(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        String[] arg = input.trim().split("\\s+");
        eventHandler.handle(new InstanceChangeEvent(arg, new RepoCollection((arg))));
    }
}
