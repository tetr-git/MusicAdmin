package routing.events;

import java.util.EventObject;

public class CliOutputEvent extends EventObject {

    private final String write;

    public CliOutputEvent(Object source, String write) {
        super(source);
        this.write = write;
    }

    public String getWrite() {
        return write;
    }

    @Override
    public String toString() {
        return "CliOutputEvent";
    }
}

