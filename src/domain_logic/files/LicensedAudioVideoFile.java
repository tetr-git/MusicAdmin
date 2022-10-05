package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.LicensedAudioVideo;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class LicensedAudioVideoFile extends MediaFile implements LicensedAudioVideo, Serializable {

    static final long serialVersionUID = 1L;
    private final int samplingRate;
    private final String holder;
    private final int resolution;

    public LicensedAudioVideoFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length, int samplingRate, String holder, int resolution) {
        super(uploader, tags, bitrate, length);
        this.samplingRate = samplingRate;
        this.holder = holder;
        this.resolution = resolution;
    }

    @Override
    public int getSamplingRate() {
        return samplingRate;
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
        return MediaTypes.LICENSEDAUDIOVIDEO.toString();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + samplingRate + "\t" + holder + "\t" + resolution;

    }
}
