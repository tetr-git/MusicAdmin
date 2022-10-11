package routing;

import java.util.Arrays;

public final class MediaAttributesCollection {
    private final Object[] attributes;
    private final int size;
    private final String type;

    public MediaAttributesCollection(String type, Object... attributes) {
        this.size = attributes.length;
        this.attributes = Arrays.copyOf(attributes, size);
        this.type = type;
    }

    public Object get(int i) {
        return attributes[i];
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
