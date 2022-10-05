package routing.listener;

import domain_logic.MediaFileRepository;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import routing.events.CliOutputEvent;
import routing.events.ReadMediaEvent;
import routing.handler.EventHandler;

import java.util.EventObject;
import java.util.HashMap;

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
        if (event.toString().equals("ReadUploaderEvent")){
            StringBuilder s = new StringBuilder("");
            //source https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
            HashMap<Uploader,Integer> map= mR.readUploaderWithCountedMediaElements();
            for (HashMap.Entry<Uploader, Integer> entry : map.entrySet()) {
                s.append(entry.getKey().getName()).append("\t").append(entry.getValue()).append("\n");
            }
            outputHandler.handle(new CliOutputEvent(event,s.toString()));
        }
    }
}
