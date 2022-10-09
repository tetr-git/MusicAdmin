package observer;

import domain_logic.MediaFileRepository;

import java.math.BigDecimal;

public class CapacityObserver implements Observer {
    private MediaFileRepository mR;

    public CapacityObserver(MediaFileRepository mR) {
        this.mR = mR;
    }

    //@source https://www.geeksforgeeks.org/bigdecimal-compareto-function-in-java/

    @Override
    public void update() {
        BigDecimal newCapacity = mR.getCurrentCapacity();
        if((newCapacity.compareTo(mR.getMaxCapacity().multiply(BigDecimal.valueOf(0.9)))) > 0) {
            System.out.println("Repository["+ mR.getNumberOfRepository()+"] reached 90% of maxCapacity");
        }
    }
}