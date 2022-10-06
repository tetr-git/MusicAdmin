package sim;

import domain_logic.MediaFileRepository;
import observer.CapacityObserver;
import observer.SimObserver;
import routing.handler.EventHandler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationOne {


    //todo Erstellen Sie einen thread der kontinuierlich versucht ein zufälliges Frachtstück einzufügen.
    // Erstellen Sie einen weiteren thread der kontinuierlich die Liste der enthaltenen Frachtstücke abruft,
    // daraus zufällig eins auswählt und entfernt.
    // Diese Simulation sollte nicht terminieren und nicht synchronisiert arbeiten.

    final Lock lock = new ReentrantLock();
    private MediaFileRepository mediaFileRepository;
    private EventHandler inputHandler;

    public SimulationOne(MediaFileRepository mediaFileRepository, EventHandler inputHandler) {
        this.mediaFileRepository = mediaFileRepository;
        this.inputHandler = inputHandler;
    }

    public void start () {
        mediaFileRepository.insertUploaderFromString("Mr Oizo");
        mediaFileRepository.insertUploaderFromString("Dj Mehdi");
        mediaFileRepository.insertUploaderFromString("SebastiAn");

        CapacityObserver capacityObserver = new CapacityObserver(mediaFileRepository);
        SimObserver simObserver = new SimObserver(mediaFileRepository);


        ThreadAddMediaElem threadAddMediaElem = new ThreadAddMediaElem(mediaFileRepository,inputHandler,lock);
        ThreadDeleteCargo threadDeleteCargo = new ThreadDeleteCargo(mediaFileRepository,inputHandler,lock);
        threadAddMediaElem.start();
        threadDeleteCargo.start();
    }
}
