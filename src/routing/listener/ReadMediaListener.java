package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.files.MediaFile;
import routing.events.CliOutputEvent;
import routing.events.ReadMediaEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class ReadMediaListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private EventHandler outputHandler;
    private EventObject event;

    public ReadMediaListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("ReadMediaEvent")){
            event = eventObject;
            for (MediaFileRepository repository : mediaFileRepoList.getRepoList()) {
                if(repository.isActiveRepository()) {
                    this.execute(repository);
                }
            }
        }
    }

    public void execute(MediaFileRepository mR) {
        StringBuilder s = new StringBuilder("Repository: "+ mR.getNumberOfRepository()+ "\n");
        if(((ReadMediaEvent)event).getReadString().equalsIgnoreCase("content")) {
            for (MediaFile m : mR.readMediaList()) {
                s.append(m.toString()).append("\n");
            }
        } else {
            for (MediaFile m : mR.readFilteredMediaElementsByClass(((ReadMediaEvent)event).getReadString())) {
                s.append(m.toString()).append("\n");
            }
        }
        outputHandler.handle(new CliOutputEvent(event,s.toString()));
    }
}
