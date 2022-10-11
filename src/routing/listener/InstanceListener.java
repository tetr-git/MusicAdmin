package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import routing.events.OutputEvent;
import routing.events.InstanceChangeEvent;
import routing.handler.EventHandler;

import java.util.EventObject;

public class InstanceListener implements EventListener {
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler outputHandler;


    public InstanceListener(MediaFileRepoList mediaFileRepoList, EventHandler outputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.outputHandler = outputHandler;
    }

    //todo output not correct

    @Override
    public void onEvent(EventObject event) {
        if (event.toString().equals("InstanceChangeEvent")) {
            String[] arg = ((InstanceChangeEvent) event).getInput().getAttributes();
            OutputEvent outputEvent;
            String returnString = "try again";
            if (arg.length == 1 && arg[0].equalsIgnoreCase("storage")) {
                mediaFileRepoList.detachAllRepositories();
                returnString = "all instances deactivated";
            } else if (arg.length > 1 && arg[0].equalsIgnoreCase("storage")) {
                StringBuilder s = new StringBuilder();
                mediaFileRepoList.changeStateAllRepositories(arg);
                for (MediaFileRepository mediaFileRepository : mediaFileRepoList.getRepoList()) {
                    if (mediaFileRepository.isActiveRepository()) {
                        s.append("Repository[").
                                append(mediaFileRepository.getNumberOfRepository()).
                                append("] active\t");
                    } else {
                        s.append("Repository[").
                                append(mediaFileRepository.getNumberOfRepository()).
                                append("] not active\t");
                    }
                }
                returnString = s.toString();
            }
            outputEvent = new OutputEvent(event, returnString);
            outputHandler.handle(outputEvent);
        }
    }
}