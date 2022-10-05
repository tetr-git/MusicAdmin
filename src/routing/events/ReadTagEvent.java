package routing.events;

import java.util.EventObject;

public class ReadTagEvent extends EventObject {

    private final String readTagString;

    public ReadTagEvent(Object source, String readTagString) {
        super(source);
        this.readTagString = readTagString;
    }

    public String getReadTagString() {
        return readTagString;
    }

    @Override
    public String toString() {
        return "ReadTagEvent";
    }
}
