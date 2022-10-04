package routing.handler;

import routing.listener.EventListener;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

public class EventHandler {
    private List<EventListener> listenerList = new LinkedList<>();
    public void add(EventListener listener) {this.listenerList.add(listener);}
    public void handle(EventObject event) {
        for (EventListener listener: listenerList) listener.onEvent(event);
    }
}
