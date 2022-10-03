package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.InteractiveVideo;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class InteractiveVideoFile extends MediaFile implements InteractiveVideo, Serializable {

    static final long serialVersionUID = 1L;
    private final String type;
    private final int resolution;

    public InteractiveVideoFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length, String type, int resolution) {
        super(uploader, tags, bitrate, length);
        this.type = type;
        this.resolution = resolution;
    }

    @Override
    public String getType() {
        return type;
    }


    @Override
    public int getResolution() {
        return resolution;
    }


    @Override
    public String typeString() {
        return MediaTypes.INTERACTIVEVIDEO.toString();
    }
}
