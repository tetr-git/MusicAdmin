package routing.events;

import util.RepoCollection;

import java.util.EventObject;

public class InstanceChangeEvent extends EventObject {

    private final RepoCollection input;

    public InstanceChangeEvent(Object source, RepoCollection input) {
        super(source);
        this.input = input;
    }

    public RepoCollection getInput() {
        return input;
    }

    @Override
    public String toString() {
        return "InstanceChangeEvent";
    }
}
