package domain_logic.files;

import domain_logic.producer.Producer;
import domain_logic.enums.Tag;
import domain_logic.file_interfaces.MediaContent;
import domain_logic.file_interfaces.Uploadable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public abstract class MediaFile implements Uploadable, MediaContent, Serializable {
    static final long serialVersionUID = 1L;
    private Collection<Tag> tags;
    private long accessCount;
    private BigDecimal bitrate;
    private Duration length;
    private BigDecimal size;
    private Producer producer;
    private Date uploadDate;

    public MediaFile(Producer producer, Collection<Tag> tags, BigDecimal bitrate, Duration length) {
        this.tags = tags;
        this.accessCount = 0;
        this.bitrate = bitrate;
        this.length = length;
        this.size = bitrate.multiply(new BigDecimal(length.getSeconds()));
        this.producer = producer;

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);
        this.uploadDate = today.getTime();
    }

    @Override
    public Collection<Tag> getTags() {
        return tags;
    }

    @Override
    public long getAccessCount() {
        return accessCount;
    }

    @Override
    public BigDecimal getBitrate() {
        return bitrate;
    }

    @Override
    public Duration getLength() {
        return length;
    }

    @Override
    public BigDecimal getSize() {
        return size;
    }

    @Override
    public Producer getUploader() {
        return producer;
    }

    @Override
    public Date getUploadDate() {
        return uploadDate;
    }

    public void updateAccessCount() {
        accessCount++;
    }

    public abstract String typeString();
}