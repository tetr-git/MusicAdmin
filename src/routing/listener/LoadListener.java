package routing.listener;

import domain_logic.MediaFileRepository;
import routing.events.ChangeEvent;
import routing.events.CliOutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class LoadListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public LoadListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        //mR.loadJos();
        String response = "Not Implemented yet";
        CliOutputEvent outputEvent;
        outputEvent = new CliOutputEvent(event,response);
        outputHandler.handle(outputEvent);
    }
}
