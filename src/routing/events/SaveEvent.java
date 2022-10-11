package routing.events;

import java.util.EventObject;

public class SaveEvent extends EventObject {

    private final String saveString;

    public SaveEvent(Object source, String readString) {
        super(source);
        this.saveString = readString;
    }

    public String getSaveString() {
        return saveString;
    }

    @Override
    public String toString() {
        return "SafeEvent";
    }
}
