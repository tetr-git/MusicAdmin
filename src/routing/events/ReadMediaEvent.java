package routing.events;

import java.util.EventObject;

public class ReadMediaEvent extends EventObject {

    private final String readString;

    public ReadMediaEvent(Object source, String readString) {
        super(source);
        this.readString = readString;
    }

    public String getReadString() {
        return readString;
    }

    @Override
    public String toString() {
        return "ReadMediaEvent";
    }
}
