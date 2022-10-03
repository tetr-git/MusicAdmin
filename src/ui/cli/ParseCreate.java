package ui.cli;

import routing.events.CreateMediaEvent;
import routing.handler.EventHandler;
import util.ParseMedia;

public class ParseCreate implements CliMode {

    EventHandler eventHandler;
    String [] arg;

    public ParseCreate(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        String[] arg = input.trim().split("\\s+");
        if (arg.length == 1) {
            //eventHandler.handle(new CreateUploaderEvent(arg[0],arg[0]));
        } else {
            ParseMedia parseMedia = new ParseMedia();
            eventHandler.handle(new CreateMediaEvent(arg, parseMedia.parseToCollection(arg)));
        }
    }
}
