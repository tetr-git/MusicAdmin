package routing.listener;

import domain_logic.MediaFileRepoList;
import routing.events.CliOutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class SaveListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;

    public SaveListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("SafeEvent")) {
            String response;
            if (mediaFileRepoList.safeJos()) {
                response = "Jos saved";
            } else {
                response = "Couldn't save Jos";
            }
            CliOutputEvent outputEvent;
            outputEvent = new CliOutputEvent(eventObject, response);
            outputHandler.handle(outputEvent);
        }
    }
}
