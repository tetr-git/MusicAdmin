package observer;

import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import routing.events.ReadTagEvent;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TagObserver implements Observer {
    private MediaFileRepository mR;
    private ArrayList<Tag> oldListOfTags;

    public TagObserver(MediaFileRepository mR) {
        this.mR = mR;
        oldListOfTags = mR.listEnumTags();
    }

    //@source https://www.geeksforgeeks.org/bigdecimal-compareto-function-in-java/

    @Override
    public void update() {
        ArrayList<Tag> tagsCurrent = mR.listEnumTags();
        for (Tag tagsNew : tagsCurrent) {
            if (!oldListOfTags.contains(tagsNew)){
                System.out.println("Tag "+tagsNew +" added");
            }
        }
        for (Tag tagsOld : oldListOfTags) {
            if (!tagsCurrent.contains(tagsOld)) {
                System.out.println("Tag " + tagsOld + " removed");
            }
        }
    }
}