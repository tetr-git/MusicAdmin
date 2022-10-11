package domain_logic.file_interfaces;

import domain_logic.producer.Uploader;

import java.util.Date;

public interface Uploadable {
    Uploader getUploader();

    Date getUploadDate();
}
