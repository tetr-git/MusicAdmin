package routing.events;

import routing.MediaAttributesCollection;

import java.util.EventObject;

public class CreateMediaEvent extends EventObject {
    private final MediaAttributesCollection attr;

    public CreateMediaEvent(Object source, MediaAttributesCollection attr) {
        super(source);
        this.attr = attr;
    }

    public MediaAttributesCollection getAttr() {
        return attr;
    }

    @Override
    public String toString() {
        return "CreateMediaElementEvent";
    }
}