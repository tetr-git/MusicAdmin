package routing.events;

import java.util.EventObject;

public class AddRepositoryEvent extends EventObject {

    private final int index;

    public AddRepositoryEvent(Object source, int index) {
        super(source);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "AddRepositoryEvent";
    }
}
