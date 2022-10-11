package sim;

import domain_logic.MediaFileRepoList;
import routing.handler.EventHandler;
import routing.parser.ParseCreate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationOne {

    final Lock lock = new ReentrantLock();
    private final MediaFileRepoList mediaFileRepoList;
    private final EventHandler inputHandler;

    public SimulationOne(MediaFileRepoList mediaFileRepoList, EventHandler inputHandler) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.inputHandler = inputHandler;
    }

    public void start() {

        ParseCreate parseCreate = new ParseCreate(inputHandler);
        parseCreate.execute("MrOizo");
        parseCreate.execute("DjMehdi");
        parseCreate.execute("SebastiAn");

        ThreadAddMediaElem threadAddMediaElem = new ThreadAddMediaElem(mediaFileRepoList, inputHandler, lock);
        ThreadDeleteCargo threadDeleteCargo = new ThreadDeleteCargo(mediaFileRepoList, inputHandler, lock);
        threadAddMediaElem.start();
        threadDeleteCargo.start();
    }
}
