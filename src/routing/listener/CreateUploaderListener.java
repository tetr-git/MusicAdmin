package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import routing.events.CliOutputEvent;
import routing.events.CreateUploaderEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class CreateUploaderListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;
    private EventObject event;


    public CreateUploaderListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("CreateUploaderEvent")){
            event = eventObject;
            for (MediaFileRepository repository : mediaFileRepoList.getRepoList()) {
                if(repository.isActiveRepository()) {
                    this.execute(repository);
                }
            }
        }
    }

    public void execute(MediaFileRepository mR) {
        StringBuilder s = new StringBuilder("Repository["+ mR.getNumberOfRepository()+"] ");
        if (mR.insertUploaderFromString(((CreateUploaderEvent)event).getUploaderString())) {
            s.append("added uploader ").append(((CreateUploaderEvent)event).getUploaderString());
        } else {
            s.append("not added uploader ").append(((CreateUploaderEvent)event).getUploaderString());
        }
        CliOutputEvent outputEvent;
        outputEvent = new CliOutputEvent(event,s.toString());
        outputHandler.handle(outputEvent);
    }
}