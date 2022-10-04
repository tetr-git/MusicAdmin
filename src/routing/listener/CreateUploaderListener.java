package routing.listener;

import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.*;
import domain_logic.producer.UploaderImpl;
import routing.events.CliOutputEvent;
import routing.events.CreateMediaEvent;
import routing.events.CreateUploaderEvent;
import routing.handler.EventHandler;
import util.MediaAttributesCollection;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.EventObject;

public class CreateUploaderListener implements EventListener {
    private MediaFileRepository mR;
    private String[] arg;
    private EventHandler outputHandler;

    public CreateUploaderListener(MediaFileRepository mR, EventHandler outputHandler) {
        this.mR = mR;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("CreateUploaderEvent")){
            String response = "not added";
            if (mR.insertUploaderFromString(((CreateUploaderEvent)event).getUploaderString())) {
                response = "added Uploader";
            }
            CliOutputEvent outputEvent;
            outputEvent = new CliOutputEvent(event,response);
            outputHandler.handle(outputEvent);
        }
    }
}