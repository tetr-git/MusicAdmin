package gui;

import domain_logic.producer.Uploader;

public class UploaderWithAmountTableWrapper {

    private final String uploader;
    private final String instance;
    private final String numberOfMediaFiles;

    public UploaderWithAmountTableWrapper(Uploader uploader, int numberOfMediaFiles, int instance) {
        this.instance = String.valueOf(instance);
        this.uploader = uploader.getName();
        this.numberOfMediaFiles = String.valueOf(numberOfMediaFiles);
    }

    public String getInstance() {
        return instance;
    }

    public String getUploader() {
        return uploader;
    }

    public String getNumberOfMediaFiles() {
        return numberOfMediaFiles;
    }
}
