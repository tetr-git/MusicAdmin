package routing.listener;

import domain_logic.MediaFileRepository;
import routing.events.ChangeEvent;
import routing.events.CliOutputEvent;
import routing.events.DeleteEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class DeleteListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public DeleteListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("DeleteEvent")){
            //todo if uploader with files gets deleted wrong message
            String response = "";
            if (mR.deleteUploader(((DeleteEvent)event).getDeleteString())) {
                response += "Uploader deleted ";
            } else if (mR.deleteMediaFiles(((DeleteEvent)event).getDeleteString())) {
                response += "Media deleted";
            } else {
                response = "nothing deleted";
            }
            CliOutputEvent outputEvent;
            outputEvent = new CliOutputEvent(event,response);
            outputHandler.handle(outputEvent);
        }
    }
}
