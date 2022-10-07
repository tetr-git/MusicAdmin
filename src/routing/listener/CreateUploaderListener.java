package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import routing.events.CliOutputEvent;
import routing.events.CreateUploaderEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class CreateUploaderListener implements EventListener {
    private MediaFileRepoList mediaFileRepoList;
    private EventHandler outputHandler;
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
                if(repository.isActive()) {
                    this.execute(repository);
                }
            }
        }
    }

    public void execute(MediaFileRepository mR) {
        String response = "not added";
        if (mR.insertUploaderFromString(((CreateUploaderEvent)event).getUploaderString())) {
            response = "added Uploader";
        }
        CliOutputEvent outputEvent;
        outputEvent = new CliOutputEvent(event,response);
        outputHandler.handle(outputEvent);
    }
}