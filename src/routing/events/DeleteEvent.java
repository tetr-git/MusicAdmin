package routing.events;

import java.util.EventObject;

public class DeleteEvent extends EventObject {

    private final String deleteString;

    public DeleteEvent(Object source, String deleteString) {
        super(source);
        this.deleteString = deleteString;
    }

    public String getDeleteString() {
        return deleteString;
    }

    @Override
    public String toString() {
        return "DeleteEvent";
    }
}