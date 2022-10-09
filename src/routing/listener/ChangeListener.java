package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import routing.events.ChangeEvent;
import routing.events.CliOutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class ChangeListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;
    private EventObject event;

    public ChangeListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("ChangeEvent")) {
            event = eventObject;
            for (MediaFileRepository repository : mediaFileRepoList.getRepoList()) {
                if(repository.isActiveRepository()) {
                    this.execute(repository);
                }
            }
        }
    }

    public void execute(MediaFileRepository mR) {
        String response;
        if (mR.updateAccessCounterMediaFile(((ChangeEvent)event).getStorageNameString())) {
            response = "Repository["+ mR.getNumberOfRepository()+ "] Counter of MediaFile with Address "+ (((ChangeEvent)event).getStorageNameString()) +" updated";
        } else
            response = "Repository["+ mR.getNumberOfRepository()+ "] Counter not updated updated";
        CliOutputEvent outputEvent;
        outputEvent = new CliOutputEvent(event,response);
        outputHandler.handle(outputEvent);
    }
}
