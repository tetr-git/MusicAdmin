package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import routing.events.OutputEvent;
import routing.events.ReadTagEvent;
import routing.handler.EventHandler;

import java.util.ArrayList;
import java.util.EventObject;

public class ReadTagListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;
    private EventObject event;

    public ReadTagListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("ReadTagEvent")) {
            event = eventObject;
            for (MediaFileRepository repository : mediaFileRepoList.getRepoList()) {
                if (repository.isActiveRepository()) {
                    this.execute(repository);
                }
            }
        }
    }

    public void execute(MediaFileRepository mR) {
        StringBuilder s = new StringBuilder("Repository[ " + mR.getNumberOfRepository() + "]");
        ArrayList<Tag> tagsCurrent = mR.listEnumTags();
        Tag[] listOfAllTags = Tag.values();
        if (!tagsCurrent.isEmpty()) {
            if (((ReadTagEvent) event).getReadTagString().equalsIgnoreCase("e")) {
                s.append(" not existing tags: \n");
                for (Tag tag : listOfAllTags) {
                    if (!tagsCurrent.contains(tag)) {
                        s.append(tag).append("\t");
                    }
                }
            } else if (((ReadTagEvent) event).getReadTagString().equalsIgnoreCase("i")) {
                s.append(" existing tags: \n");
                for (Tag tag : listOfAllTags) {
                    if (tagsCurrent.contains(tag)) {
                        s.append(tag).append("\t");
                    }
                }
            } else {
                s = new StringBuilder("Wrong Input");
            }
        } else {
            s = new StringBuilder("Repository[" + mR.getNumberOfRepository() + "] no tags found");
        }

        outputHandler.handle(new OutputEvent(event, s.toString()));
    }
}
