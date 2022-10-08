package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import routing.events.CliOutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class LoadListener implements EventListener {
    private MediaFileRepoList mediaFileRepoList;
    private EventHandler outputHandler;

    public LoadListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("LoadEvent")) {
            String response;
            if (mediaFileRepoList.loadJos()){
                response = "Jos Loaded";
            } else {
                response = "Couldn't load";
            }
            CliOutputEvent outputEvent = new CliOutputEvent(eventObject,response);
            outputHandler.handle(outputEvent);
        }
    }

}
