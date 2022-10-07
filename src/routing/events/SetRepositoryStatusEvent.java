package routing.events;

import util.RepoCollection;

import java.util.EventObject;

public class SetRepositoryStatusEvent extends EventObject {

    private final RepoCollection input;

    public SetRepositoryStatusEvent(Object source, RepoCollection input) {
        super(source);
        this.input = input;
    }

    public RepoCollection getInput() {
        return input;
    }

    @Override
    public String toString() {
        return "SetRepositoryStatusEvent";
    }
}
