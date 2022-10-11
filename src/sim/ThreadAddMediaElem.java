package sim;

import domain_logic.MediaFileRepoList;
import routing.ParseMediaCollector;
import routing.events.CreateMediaEvent;
import routing.handler.EventHandler;

import java.util.concurrent.locks.Lock;

public class ThreadAddMediaElem extends Thread {

    MediaFileRepoList mediaFileRepoList;
    EventHandler inputHandler;
    Lock lock;

    public ThreadAddMediaElem(MediaFileRepoList mediaFileRepoList, EventHandler inputHandler, Lock lock) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.inputHandler = inputHandler;
        this.lock = lock;
    }

    public void run() {
        while (true) {
            lock.lock();
            GenRandomMediaElem randomMediaElem = new GenRandomMediaElem();
            String[] arg = randomMediaElem.generateRandomMedia();
            ParseMediaCollector parseMediaCollector = new ParseMediaCollector();
            CreateMediaEvent createMediaEvent = new CreateMediaEvent(arg, parseMediaCollector.parseToCollection(arg));
            inputHandler.handle(createMediaEvent);
            lock.unlock();
        }

    }
}
