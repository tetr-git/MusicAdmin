package routing.parser;

import routing.ParseMediaCollector;
import routing.events.CreateMediaEvent;
import routing.events.CreateUploaderEvent;
import routing.handler.EventHandler;

public class ParseCreate {

    EventHandler eventHandler;

    public ParseCreate(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        String[] arg = input.trim().split("\\s+");
        if (arg.length == 1) {
            eventHandler.handle(new CreateUploaderEvent(arg[0], arg[0]));
        } else {
            ParseMediaCollector parseMediaCollector = new ParseMediaCollector();
            eventHandler.handle(new CreateMediaEvent(arg, parseMediaCollector.parseToCollection(arg)));
        }
    }
}
