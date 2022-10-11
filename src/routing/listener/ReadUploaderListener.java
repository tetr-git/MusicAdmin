package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.producer.Uploader;
import routing.events.OutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ReadUploaderListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;
    private EventObject event;


    public ReadUploaderListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("ReadUploaderEvent")) {
            event = eventObject;
            for (MediaFileRepository repository : mediaFileRepoList.getRepoList()) {
                if (repository.isActiveRepository()) {
                    this.execute(repository);
                }
            }
        }
    }

    private void execute(MediaFileRepository mR) {
        StringBuilder s = new StringBuilder("Repository[" + mR.getNumberOfRepository() + "]");
        /**
         *  source https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
         */

        LinkedHashMap<Uploader, Integer> map = mR.readUploaderWithCountedMediaElements();
        for (HashMap.Entry<Uploader, Integer> entry : map.entrySet()) {
            s.append("\n").append(entry.getKey().getName()).append("\t").append(entry.getValue());
        }
        if (mR.readUploaderWithCountedMediaElements().isEmpty()) {
            s.append(" is empty");
        }
        outputHandler.handle(new OutputEvent(event, s.toString()));
    }
}
