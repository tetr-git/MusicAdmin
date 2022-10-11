package sim;

import domain_logic.MediaFileRepository;
import routing.events.CreateMediaEvent;
import routing.handler.EventHandler;
import routing.ParseMediaCollector;

import java.util.concurrent.locks.Lock;

public class ThreadAddMediaElem extends Thread{

    MediaFileRepository mediaFileRepository;
    EventHandler eHandler;
    Lock lock;
    String uploader;

    public ThreadAddMediaElem(MediaFileRepository mediaFileRepository, EventHandler eHandler, Lock lock) {
        this.mediaFileRepository = mediaFileRepository;
        this.eHandler = eHandler;
        this.lock = lock;
        this.uploader = uploader;
    }

    public void run() {
        while ( true) {
            lock.lock();
            GenRandomMediaElem m = new GenRandomMediaElem();
            String[] arg = m.generateRandomMedia();
            ParseMediaCollector parseMediaCollector = new ParseMediaCollector();
            CreateMediaEvent e = new CreateMediaEvent(arg, parseMediaCollector.parseToCollection(arg));
            eHandler.handle(e);
            lock.unlock();
        }

    }
}
