package routing.listener;

import domain_logic.MediaFileRepository;
import domain_logic.files.MediaFile;
import routing.events.CliOutputEvent;
import routing.events.ReadMediaEvent;
import routing.handler.EventHandler;

import java.util.EventObject;
import java.util.LinkedList;

public class ReadMediaListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public ReadMediaListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("ReadMediaEvent")){
            StringBuilder s = new StringBuilder("");
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
}
