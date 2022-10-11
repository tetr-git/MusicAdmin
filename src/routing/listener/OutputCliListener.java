package routing.listener;

import cli.ConsoleManagement;
import routing.events.OutputEvent;

import java.util.EventObject;

public class OutputCliListener implements EventListener {
    private final ConsoleManagement cM;

    public OutputCliListener(ConsoleManagement cM) {
        this.cM = cM;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("OutputEvent")) {
            cM.writeToConsole(((OutputEvent) event).getWrite());
        }
    }
}
