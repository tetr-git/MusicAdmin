package routing.listener;

import domain_logic.MediaFileRepository;
import routing.events.CliOutputEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class SaveListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public SaveListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("SafeEvent")) {
            String response;
            if (mR.safeJos()) {
                response = "Jos saved";
            } else {
                response = "Couldn't save Jos";
            }
            CliOutputEvent outputEvent;
            outputEvent = new CliOutputEvent(event,response);
            outputHandler.handle(outputEvent);
        }
    }
}
