package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.LicensedAudio;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class LicensedAudioFile extends MediaFile implements LicensedAudio, Serializable {
    static final long serialVersionUID = 1L;
    private final int samplingRate;
    private final String holder;

    public LicensedAudioFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length, int samplingRate, String holder) {
        super(uploader, tags, bitrate, length);
        this.samplingRate = samplingRate;
        this.holder = holder;
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
    public String typeString() {
        return MediaTypes.LICENSEDAUDIO.toString();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + samplingRate + "\t" + holder;
    }
}
