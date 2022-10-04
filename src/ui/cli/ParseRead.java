package ui.cli;


import routing.events.ReadMediaEvent;
import routing.events.ReadUploaderEvent;
import routing.handler.EventHandler;

public class ParseRead {

    EventHandler eventHandler;

    public ParseRead(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void execute(String input) {
        String[] arg = input.trim().split("\\s+");
        if (arg.length == 1 && input.equalsIgnoreCase("uploader")) {
            new ReadUploaderEvent(input,input);
        } else if (arg.length == 1 && input.equalsIgnoreCase("content")){
            new ReadMediaEvent(input,input);
        } else if(arg.length == 2 && arg[0].equalsIgnoreCase("content")) {
            new ReadMediaEvent(input,arg[1]);
        }
    }
}
