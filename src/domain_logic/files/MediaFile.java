package domain_logic.files;

import domain_logic.enums.Tag;
import domain_logic.file_interfaces.Content;
import domain_logic.file_interfaces.MediaContent;
import domain_logic.file_interfaces.Uploadable;
import domain_logic.producer.Uploader;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public abstract class MediaFile implements Uploadable, MediaContent, Content, Serializable {

    static final long serialVersionUID = 1L;
    private final UploaderImpl uploader;
    private final Collection<Tag> tags;
    private long accessCount;
    private final BigDecimal bitrate;
    private final Duration length;
    private final BigDecimal size;
    private final Date uploadDate;
    private String address;

    public MediaFile(UploaderImpl uploader, Collection<Tag> tags, BigDecimal bitrate, Duration length) {
        this.uploader = uploader;
        this.tags = tags;
        this.accessCount = 0;
        this.bitrate = bitrate;
        this.length = length;
        this.size = (bitrate.multiply(BigDecimal.valueOf(length.getSeconds() * 0.001)));

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        this.uploadDate = today.getTime();
        this.address = "";
    }

    @Override
    public String getAddress() {
        return address;
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
    public Uploader getUploader() {
        return uploader;
    }

    @Override
    public Date getUploadDate() {
        return uploadDate;
    }

    public void updateAccessCount() {
        accessCount++;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract String typeString();

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return address + "\t" + typeString() + "\t" + uploader.getName() + "\t" + tags + "\t" + accessCount + "\t" + bitrate + "\t" + length + "\t" + size + "\t" + sdf.format(uploadDate);
    }
}
