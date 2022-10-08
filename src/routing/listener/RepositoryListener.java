package routing.listener;

import domain_logic.MediaFileRepoList;
import routing.events.CliOutputEvent;
import routing.events.SetRepositoryStatusEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class RepositoryListener implements EventListener {
    private MediaFileRepoList mediaFileRepoList;
    private EventHandler outputHandler;


    public RepositoryListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    //todo output not correct

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("SetRepositoryStatusEvent")){
            String [] arg = ((SetRepositoryStatusEvent) event).getInput().getAttributes();
            CliOutputEvent outputEvent;
            String returnString = "try again";
            if (arg.length == 1 && arg[0].equalsIgnoreCase("storage")) {
                mediaFileRepoList.detachAllRepositories();
                returnString = "detached all Repositories";
            } else if (arg.length>1 && arg[0].equalsIgnoreCase("storage")){
                mediaFileRepoList.changeStateAllRepositories(arg);
                returnString = "detached x";
            }
            outputEvent = new CliOutputEvent(event,returnString);
            outputHandler.handle(outputEvent);
        }
    }
}