package routing.listener;

import domain_logic.MediaFileRepository;
import routing.events.ChangeEvent;
import routing.events.CliOutputEvent;
import routing.events.CreateUploaderEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class ChangeListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public ChangeListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        String response = "Counter not updated";
        if (mR.updateAccessCounterMediaFile(((ChangeEvent)event).getStorageNameString())) {
            response = "Counter updated";
        }
        CliOutputEvent outputEvent;
        outputEvent = new CliOutputEvent(event,response);
        outputHandler.handle(outputEvent);
    }
}
