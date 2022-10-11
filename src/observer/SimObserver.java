package observer;

import domain_logic.MediaFileRepository;

import java.io.Serializable;

public class SimObserver implements Observer, Serializable {
    static final long serialVersionUID = 1L;
    private final MediaFileRepository mR;
    private int currentNumberOfMediaElements;

    public SimObserver(MediaFileRepository mR) {
        this.mR = mR;
        this.currentNumberOfMediaElements = this.mR.getCurrentNumberOfMediaElements();
    }

    @Override
    public void update() {
        int newAmount = mR.getCurrentNumberOfMediaElements();
        if (newAmount > currentNumberOfMediaElements) {
            System.out.println("MediaElement added");
        } else if (newAmount < currentNumberOfMediaElements) {
            System.out.println("MediaElement deleted");
        }
        currentNumberOfMediaElements = newAmount;
    }
}
