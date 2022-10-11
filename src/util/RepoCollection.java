package util;

import java.util.Arrays;

public final class RepoCollection {
    private final String[] attributes;

    public RepoCollection(String... attributes) {
        int size = attributes.length;
        this.attributes = Arrays.copyOf(attributes, size);
    }

    public String[] getAttributes() {
        return attributes;
    }
}
