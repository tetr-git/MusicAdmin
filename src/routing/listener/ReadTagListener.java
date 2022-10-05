package routing.listener;

import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import routing.events.CliOutputEvent;
import routing.events.ReadMediaEvent;
import routing.events.ReadTagEvent;
import routing.handler.EventHandler;

import java.util.*;

public class ReadTagListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public ReadTagListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("ReadTagEvent")){
            StringBuilder s = new StringBuilder("");
            ArrayList<Tag> tagsCurrent = mR.listEnumTags();
            Tag[] listOfAllTags = Tag.values();
            if (!tagsCurrent.isEmpty()) {
                if(((ReadTagEvent)event).getReadTagString().equalsIgnoreCase("e")) {
                    for (Tag tag : listOfAllTags) {
                        if (!tagsCurrent.contains(tag)) {
                            s.append(tag).append("\t");
                        }
                    }
                }else if(((ReadTagEvent)event).getReadTagString().equalsIgnoreCase("i")) {
                    for (Tag tag : listOfAllTags) {
                        if (tagsCurrent.contains(tag)) {
                            s.append(tag).append("\t");
                            }
                        }
                    }
                }
            outputHandler.handle(new CliOutputEvent(event,s.toString()));
        }
    }
}
