package routing.events;

import java.util.EventObject;

public class LoadEvent extends EventObject {
    private final String LoadString;

    public LoadEvent(Object source, String readString) {
        super(source);
        this.LoadString = readString;
    }

    public String getLoadString() {
        return LoadString;
    }

    @Override
    public String toString() {
        return "LoadEvent";
    }
}

