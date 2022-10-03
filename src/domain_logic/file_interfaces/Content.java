package domain_logic.file_interfaces;

import domain_logic.enums.Tag;

import java.util.Collection;

public interface Content {
    String getAddress();
    Collection<Tag> getTags();
    long getAccessCount();
}
