package routing.listener;

import domain_logic.MediaFileRepository;
import routing.events.CliOutputEvent;
import routing.events.ReadMediaEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

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
        CliOutputEvent outputEvent;
        if(((ReadMediaEvent)event).getReadString().equalsIgnoreCase("content")) {
            outputEvent = new CliOutputEvent(event,mR.readMediaList().toString());
        } else {
            outputEvent = new CliOutputEvent(event,mR.readFilteredMediaElementsByClass(((ReadMediaEvent)event).getReadString()).toString());
        }
        outputHandler.handle(outputEvent);
    }
}
