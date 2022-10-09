package ui.gui;

import domain_logic.file_interfaces.*;
import domain_logic.files.MediaFile;
import util.FormatIntervalToString;

import java.util.Arrays;


public class MediaWrapper {

    private String type;
    private String address;
    private String tags;
    private String accessCount;
    private String bitrate;
    private String length;
    private String size;
    private String uploader;
    private String uploadDate;
    private String samplingRate;
    private String resolution;
    private String typeInteractive;
    private String holder;


    public MediaWrapper(MediaFile mediaElement) {
        type = mediaElement.getClass().getSimpleName();
        address = mediaElement.getAddress();
        tags = Arrays.toString(mediaElement.getTags().toArray());
        accessCount = String.valueOf(mediaElement.getAccessCount());
        bitrate = String.valueOf(mediaElement.getBitrate());
        length = FormatIntervalToString.formatInterval(mediaElement.getLength().toMillis());
        size = mediaElement.getSize().toString();
        uploader = mediaElement.getUploader().getName();
        uploadDate = mediaElement.getUploadDate().toString();
        //audio
        try {
            samplingRate = String.valueOf(((Audio)mediaElement).getSamplingRate());
        } catch (Exception ignored) {}
        //audioVideo
        try {
            samplingRate = String.valueOf(((Audio)mediaElement).getSamplingRate());
            resolution = String.valueOf(((AudioVideo)mediaElement).getResolution());
        } catch (Exception ignored) {}
        //InteractiveVideo
        try {
            resolution = String.valueOf(((InteractiveVideo)mediaElement).getResolution());
            typeInteractive =((InteractiveVideo)mediaElement).getType();
        } catch (Exception ignored) {}
        //licensedAudio
        try {
            samplingRate = String.valueOf(((LicensedAudio)mediaElement).getSamplingRate());
            holder = ((LicensedAudio)mediaElement).getHolder();
        } catch (Exception ignored) {}
        //licensedAudioVideo
        try {
            samplingRate = String.valueOf(((LicensedAudioVideo)mediaElement).getSamplingRate());
            holder = ((LicensedAudioVideo)mediaElement).getHolder();
            resolution = String.valueOf(((LicensedAudioVideo)mediaElement).getResolution());
        } catch (Exception ignored) {}
        //licensedVideo
        try {
            holder = ((LicensedVideo)mediaElement).getHolder();
            resolution = String.valueOf(((LicensedVideo)mediaElement).getResolution());
        } catch (Exception ignored) {}
        //video
        try {
            resolution = String.valueOf(((Video)mediaElement).getResolution());
        } catch (Exception ignored) {}
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getTags() {
        return tags;
    }

    public String getAccessCount() {
        return accessCount;
    }

    public String getBitrate() {
        return bitrate;
    }

    public String getLength() {
        return length;
    }

    public String getSize() {
        return size;
    }

    public String getUploader() {
        return uploader;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getSamplingRate() {
        return samplingRate;
    }

    public String getResolution() {
        return resolution;
    }

    public String getTypeInteractive() {
        return typeInteractive;
    }

    public String getHolder() {
        return holder;
    }
}
