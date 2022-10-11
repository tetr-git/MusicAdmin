package routing.listener;

import net.ServerHandler;
import routing.events.OutputEvent;

import java.util.EventObject;

public class OutputNetClientListener implements EventListener {
    private final ServerHandler serverHandler;

    public OutputNetClientListener(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("OutputEvent")) {
            serverHandler.writeToConsole(((OutputEvent) event).getWrite());
        }
    }
}
