package routing.events;

import java.util.EventObject;

public class ReadUploaderEvent extends EventObject {

    private String read;

    public ReadUploaderEvent(Object source , String read) {
        super(source);
        this.read = read;
    }

    public String getRead() {
        return read;
    }

    @Override
    public String toString() {
        return "ReadUploaderEvent";
    }
}
