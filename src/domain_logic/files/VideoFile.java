package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.Video;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class VideoFile extends MediaFile implements Video, Serializable {

    private final int resolution;

    public VideoFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length, int resolution) {
        super(uploader, tags, bitrate, length);
        this.resolution = resolution;
    }

    @Override
    public int getResolution() {
        return resolution;
    }

    @Override
    public String typeString() {
        return MediaTypes.ViDEO.toString();
    }
}
