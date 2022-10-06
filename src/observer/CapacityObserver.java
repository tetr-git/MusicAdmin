package observer;

import domain_logic.MediaFileRepository;

import java.math.BigDecimal;

public class CapacityObserver implements Observer {
    private MediaFileRepository mR;

    public CapacityObserver(MediaFileRepository mR) {
        this.mR = mR;
    }
    /*
    https://www.geeksforgeeks.org/bigdecimal-compareto-function-in-java/
     */

    @Override
    public void update() {
        BigDecimal newCapacity = mR.getCurrentCapacity();
        if((newCapacity.compareTo(mR.getMaxCapacity().multiply(BigDecimal.valueOf(0.9)))) > 0) {
            System.out.println("Reached 90% of maxCapacity");
        }
    }
}

/*
public class KonkreterBeobachter implements Beobachter {
    private KonkretesSubjekt konkretesSubjekt;
    private int alterZustand;
    public KonkreterBeobachter(KonkretesSubjekt konkretesSubjekt) {
        this.konkretesSubjekt = konkretesSubjekt;
        this.konkretesSubjekt.meldeAn(this);
        this.alterZustand=this.konkretesSubjekt.gibZustand();
    }
    @Override
    public void aktualisiere() {
        int newState = konkretesSubjekt.gibZustand();
        if(newState!=this.alterZustand) {
            System.out.println("neuer Zustand=" + newState);
            this.alterZustand=newState;
        }
    }
}
 */