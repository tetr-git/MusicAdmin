package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.producer.Uploader;
import routing.events.CliOutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;
import java.util.HashMap;

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
        if (eventObject.toString().equals("ReadUploaderEvent")){
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
        //source https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        HashMap<Uploader,Integer> map= mR.readUploaderWithCountedMediaElements();
        for (HashMap.Entry<Uploader, Integer> entry : map.entrySet()) {
            s.append(entry.getKey().getName()).append("\t").append(entry.getValue()).append("\n");
        }
        outputHandler.handle(new CliOutputEvent(event,s.toString()));
    }
}
