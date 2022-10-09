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
        StringBuilder stringBuilder = new StringBuilder("Repository["+ mR.getNumberOfRepository()+"]");
        boolean change = false;
        for (Tag tagsNew : tagsCurrent) {
            if (!oldListOfTags.contains(tagsNew)){
                stringBuilder.append("\tTag ").append(tagsNew).append(" added");
                change= true;
            }
        }
        for (Tag tagsOld : oldListOfTags) {
            if (!tagsCurrent.contains(tagsOld)) {
                stringBuilder.append("\tTag ").append(tagsOld).append(" removed");
                change = true;
            }
        }
        if (change) {
            System.out.println(stringBuilder);
        }
        oldListOfTags = tagsCurrent;
    }


}