package routing.listener;

import routing.events.CliOutputEvent;
import ui.cli.ConsoleManagement;

import java.util.EventObject;

public class CliOutputListener implements EventListener {
    private ConsoleManagement cM;

    public CliOutputListener(ConsoleManagement cM) {
        this.cM = cM;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("CliOutputEvent")) {
            cM.writeToConsole(((CliOutputEvent)event).getWrite());
        }
    }
}
