package routing.events;

import java.util.EventObject;

public class LoadEvent extends EventObject {
    private final String loadString;

    public LoadEvent(Object source, String loadString) {
        super(source);
        this.loadString = loadString;
    }

    public String getLoadString() {
        return loadString;
    }

    @Override
    public String toString() {
        return "LoadEvent";
    }
}

