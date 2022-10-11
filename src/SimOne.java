import domain_logic.MediaFileRepoList;
import observer.OberserverTyp;
import routing.handler.EventHandler;
import routing.listener.OutputCliListener;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import routing.listener.DeleteListener;
import sim.SimulationOne;
import ui.cli.ConsoleManagement;

import java.math.BigDecimal;

public class SimOne {
    public static void main(String[] args) {
        MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(1000));
        mediaFileRepoList.attachObserverToList(OberserverTyp.capacity);
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        ConsoleManagement consoleManagement = new ConsoleManagement(inputHandler);
        inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepoList, outputHandler));
        inputHandler.add(new DeleteListener(mediaFileRepoList, outputHandler));
        outputHandler.add(new OutputCliListener(consoleManagement));
        SimulationOne simulationOne = new SimulationOne(mediaFileRepoList, inputHandler);
        simulationOne.start();
    }
}
