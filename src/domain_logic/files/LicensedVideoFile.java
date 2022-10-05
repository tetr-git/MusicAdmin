package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.LicensedVideo;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class LicensedVideoFile extends MediaFile implements LicensedVideo, Serializable {

    static final long serialVersionUID = 1L;
    private final String holder;
    private final int resolution;

    public LicensedVideoFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length, String holder, int resolution) {
        super(uploader, tags, bitrate, length);
        this.holder = holder;
        this.resolution = resolution;
    }

    @Override
    public String getHolder() {
        return holder;
    }

    @Override
    public int getResolution() {
        return resolution;
    }

    @Override
    public String typeString() {
        return MediaTypes.LICENSEDVIDEO.toString();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + holder + "\t" + resolution;
    }
}
