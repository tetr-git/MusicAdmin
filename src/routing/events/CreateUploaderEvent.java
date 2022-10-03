package routing.events;

import java.util.EventObject;

public class CreateUploaderEvent extends EventObject {

    private String uploaderString;

    public CreateUploaderEvent(Object source, String uploaderString) {
        super(source);
        this.uploaderString = uploaderString;
    }

    public String getUploaderString() {
        return uploaderString;
    }

    @Override
    public String toString() {
        return "CreateUploaderEvent";
    }
}
