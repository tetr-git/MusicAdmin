package routing.events;

import java.util.EventObject;

public class ChangeEvent extends EventObject {

    private final String storageNameString;

    public ChangeEvent(Object source, String storageNameString) {
        super(source);
        this.storageNameString = storageNameString;
    }

    public String getStorageNameString() {
        return storageNameString;
    }

    public String toString() {
        return "ChangeEvent";
    }
}
