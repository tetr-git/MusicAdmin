package routing.listener;

import domain_logic.MediaFileRepoList;
import routing.events.OutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class LoadListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;

    public LoadListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("LoadEvent")) {
            String response;
            if (mediaFileRepoList.loadJos()) {
                response = "Jos Loaded";
            } else {
                response = "Couldn't load File";
            }
            OutputEvent outputEvent = new OutputEvent(eventObject, response);
            outputHandler.handle(outputEvent);
        }
    }

}
