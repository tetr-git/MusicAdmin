package sim;

import domain_logic.MediaFileRepoList;
import domain_logic.files.MediaFile;
import routing.events.DeleteEvent;
import routing.handler.EventHandler;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class ThreadDeleteCargo extends Thread{

    private MediaFileRepoList mediaFileRepoList;
    private EventHandler eventHandler;
    private Lock lock;
    Random rand = new Random();

    public ThreadDeleteCargo(MediaFileRepoList mediaFileRepoList, EventHandler eventHandler, Lock lock) {
        this.mediaFileRepoList = mediaFileRepoList;
        this.eventHandler = eventHandler;
        this.lock = lock;
    }

    public void run() {
        while ( true) {

            lock.lock();
            ArrayList<String> addresses = new ArrayList<>();
            for (MediaFile i : mediaFileRepoList.getSingleRepository(0).readMediaList()) {
                addresses.add(i.getAddress());
            }
            String[] stockArr = new String[addresses.size()];
            stockArr = addresses.toArray(stockArr);
            Random rand = new Random();
            //todo: last minute fix
            //throws out of Bound with empty list
            //if list is not empty..
            if((addresses.size()-1>0)) {
                int int_random = rand.nextInt(addresses.size()-1);
                DeleteEvent deleteEvent = new DeleteEvent(stockArr[int_random],stockArr[int_random]);
                eventHandler.handle(deleteEvent);
            }
            lock.unlock();
        }
    }
}