package routing.listener;

import domain_logic.MediaFileRepository;
import routing.events.CliOutputEvent;
import routing.events.ReadMediaEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class ReadUploaderListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public ReadUploaderListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        CliOutputEvent outputEvent;
        outputEvent = new CliOutputEvent(event,mR.readUploaderWithCountedMediaElements().toString());
        outputHandler.handle(outputEvent);
    }
}
