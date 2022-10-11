package sim;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import routing.events.CreateMediaEvent;
import routing.handler.EventHandler;
import routing.ParseMediaCollector;
import util.GenRandomMediaElem;

import java.util.concurrent.locks.Lock;

public class ThreadAddMediaElem extends Thread{

    MediaFileRepoList mediaFileRepoList;
    EventHandler inputHandler;
    Lock lock;
    String uploader;

    public ThreadAddMediaElem(MediaFileRepoList mediaFileRepoList, EventHandler inputHandler, Lock lock) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.inputHandler = inputHandler;
        this.lock = lock;
        this.uploader = uploader;
    }

    public void run() {
        while ( true) {
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
