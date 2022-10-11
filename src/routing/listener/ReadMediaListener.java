package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.files.MediaFile;
import routing.events.CliOutputEvent;
import routing.events.ReadMediaEvent;
import routing.handler.EventHandler;

import java.text.SimpleDateFormat;
import java.util.EventObject;

public class ReadMediaListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;
    private EventObject event;

    public ReadMediaListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("ReadMediaEvent")) {
            event = eventObject;
            for (MediaFileRepository repository : mediaFileRepoList.getRepoList()) {
                if (repository.isActiveRepository()) {
                    this.execute(repository);
                }
            }
        }
    }

    public void execute(MediaFileRepository mR) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder s = new StringBuilder("Repository: " + mR.getNumberOfRepository());
        if (((ReadMediaEvent) event).getReadString().equalsIgnoreCase("content")) {
            for (MediaFile m : mR.readMediaList()) {
                s.append("\n").
                        append(m.typeString()).append("\t").
                        append(m.getAddress()).append("\t").
                        append(sdf.format(m.getUploadDate())).append("\t").
                        append(m.getAccessCount());
            }
        } else {
            for (MediaFile m : mR.readFilteredMediaElementsByClass(((ReadMediaEvent) event).getReadString())) {
                s.append("\n").
                        append(m.typeString()).append("\t").
                        append(m.getAddress()).append("\t").
                        append(sdf.format(m.getUploadDate())).append("\t").
                        append(m.getAccessCount());
            }
        }
        outputHandler.handle(new CliOutputEvent(event, s.toString()));
    }
}
