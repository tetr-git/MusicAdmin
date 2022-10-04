package ui.cli;

import routing.events.ChangeEvent;
import routing.events.CreateMediaEvent;
import routing.events.CreateUploaderEvent;
import routing.handler.EventHandler;
import util.ParseMedia;

public class ParseUpdate implements CliMode {

    EventHandler eventHandler;

    public ParseUpdate(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {

        new ChangeEvent(input,input);
    }
}
