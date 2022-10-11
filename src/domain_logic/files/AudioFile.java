package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.Audio;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class AudioFile extends MediaFile implements Audio, Serializable {

    static final long serialVersionUID = 1L;
    private final int samplingRate;

    public AudioFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length, int samplingRate) {
        super(uploader, tags, bitrate, length);
        this.samplingRate = samplingRate;
    }

    @Override
    public int getSamplingRate() {
        return samplingRate;
    }

    @Override
    public String typeString() {
        return MediaTypes.AUDIO.toString();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + samplingRate;
    }
}
