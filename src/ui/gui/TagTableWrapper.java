package ui.gui;

import domain_logic.enums.Tag;

public class TagTableWrapper {

    private final String instance;
    private final String tag;

    public TagTableWrapper(Tag tag, int instance) {
        this.instance = String.valueOf(instance);
        this.tag = String.valueOf(tag);
    }

    public String getInstance() {
        return instance;
    }

    public String getTag() {
        return tag;
    }
}
