package routing.events;

import java.util.EventObject;

public class OutputEvent extends EventObject {

    private final String write;

    public OutputEvent(Object source, String write) {
        super(source);
        this.write = write;
    }

    public String getWrite() {
        return write;
    }

    @Override
    public String toString() {
        return "OutputEvent";
    }
}

