package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.AudioVideo;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class AudioVideoFile extends MediaFile implements AudioVideo, Serializable {
    static final long serialVersionUID = 1L;
    private final int samplingRate;
    private final int resolution;

    public AudioVideoFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length, String address, int samplingRate, int resolution) {
        super(uploader, tags, bitrate, length, address);
        this.samplingRate = samplingRate;
        this.resolution = resolution;
    }

    @Override
    public int getSamplingRate() {
        return samplingRate;
    }

    @Override
    public int getResolution() {
        return resolution;
    }

    @Override
    public String typeString() {
        return MediaTypes.AUDIOVIDEO.toString();
    }
}
