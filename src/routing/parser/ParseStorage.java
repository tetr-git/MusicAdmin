package routing.parser;

import routing.events.CreateMediaEvent;
import routing.events.CreateUploaderEvent;
import routing.events.SetRepositoryStatusEvent;
import routing.handler.EventHandler;
import util.ParseMedia;
import util.RepoCollection;

public class ParseStorage {

    EventHandler eventHandler;

    public ParseStorage(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        String[] arg = input.trim().split("\\s+");
        eventHandler.handle(new SetRepositoryStatusEvent(arg,new RepoCollection((arg))));
    }
}
