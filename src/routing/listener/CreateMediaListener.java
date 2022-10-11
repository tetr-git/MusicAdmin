package routing.listener;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.*;
import domain_logic.producer.UploaderImpl;
import routing.MediaAttributesCollection;
import routing.events.CreateMediaEvent;
import routing.events.OutputEvent;
import routing.handler.EventHandler;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.EventObject;

public class CreateMediaListener implements EventListener {
    private final MediaFileRepoList list;
    private final EventHandler outputHandler;
    private EventObject event;

    public CreateMediaListener(MediaFileRepoList list, EventHandler outputHandler) {
        this.list = list;
        this.outputHandler = outputHandler;
    }

    @Override
    public void onEvent(EventObject eventObject) {
        if (eventObject.toString().equals("CreateMediaElementEvent")) {
            event = eventObject;
            for (MediaFileRepository repository : list.getRepoList()) {
                if (repository.isActiveRepository()) {
                    this.execute(repository);
                }
            }
        }
    }

    private void execute(MediaFileRepository mR) {
        MediaAttributesCollection arg = ((CreateMediaEvent) event).getAttr();
        UploaderImpl uploader = new UploaderImpl((String) arg.get(0));
        boolean addedMedia = false;
        OutputEvent outputEvent;
        switch (arg.getType()) {
            case "audio":
                if (arg.getSize() == 5) {
                    //full argumentList
                    AudioFile audio = new AudioFile(uploader, (Collection<Tag>) arg.get(1),
                            (BigDecimal) arg.get(2), (Duration) arg.get(3), (Integer) arg.get(4));
                    if (mR.insertMediaFile(audio)) {
                        addedMedia = true;
                    }
                }
                if (arg.getSize() == 4) {
                    AudioFile audio = new AudioFile(uploader, (Collection<Tag>) arg.get(1),
                            (BigDecimal) arg.get(2), (Duration) arg.get(3), 0);
                    if (mR.insertMediaFile(audio)) {
                        addedMedia = true;
                    }
                }
                break;
            case "audiovideo":
                if (arg.getSize() == 6) {
                    //full argumentList
                    AudioVideoFile audioVideo = new AudioVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3), (Integer) arg.get(4), (Integer) arg.get(5));
                    if (mR.insertMediaFile(audioVideo)) {
                        addedMedia = true;
                    }
                }
                if (arg.getSize() == 4) {
                    AudioVideoFile audioVideo = new AudioVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3), 0, 0);
                    if (mR.insertMediaFile(audioVideo)) {
                        addedMedia = true;
                    }
                }
                break;
            case "interactivevideo":
                if (arg.getSize() == 6) {
                    //full argumentList
                    InteractiveVideoFile interactiveVideo = new InteractiveVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3), (String) arg.get(4), (Integer) arg.get(5));
                    if (mR.insertMediaFile(interactiveVideo)) {
                        addedMedia = true;
                    }
                }
                if (arg.getSize() == 4) {
                    InteractiveVideoFile interactiveVideo = new InteractiveVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            "", 0);
                    if (mR.insertMediaFile(interactiveVideo)) {
                        addedMedia = true;
                    }
                }
                break;
            case "licensedaudio":
                if (arg.getSize() == 6) {
                    //full argumentList
                    LicensedAudioFile licensedAudio = new LicensedAudioFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            (Integer) arg.get(4), (String) arg.get(5));
                    if (mR.insertMediaFile(licensedAudio)) {
                        addedMedia = true;
                    }
                }
                if (arg.getSize() == 4) {
                    LicensedAudioFile licensedAudio = new LicensedAudioFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            0, "");
                    if (mR.insertMediaFile(licensedAudio)) {
                        addedMedia = true;
                    }
                }
                break;
            case "licensedaudiovideo":
                if (arg.getSize() == 7) {
                    //full argumentList
                    LicensedAudioVideoFile licensedAudioVideo = new LicensedAudioVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            (Integer) arg.get(4), (String) arg.get(5), (Integer) arg.get(6));
                    if (mR.insertMediaFile(licensedAudioVideo)) {
                        addedMedia = true;
                    }
                }
                if (arg.getSize() == 4) {
                    LicensedAudioVideoFile licensedAudioVideo = new LicensedAudioVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            0, "", 0);
                    if (mR.insertMediaFile(licensedAudioVideo)) {
                        addedMedia = true;
                    }
                }
                break;
            case "licensedvideo":
                if (arg.getSize() == 6) {
                    //full argumentList
                    LicensedVideoFile licensedVideo = new LicensedVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            (String) arg.get(4), (Integer) arg.get(5));
                    if (mR.insertMediaFile(licensedVideo)) {
                        addedMedia = true;
                    }
                }
                if (arg.getSize() == 4) {
                    LicensedVideoFile licensedVideo = new LicensedVideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            "", 0);
                    if (mR.insertMediaFile(licensedVideo)) {
                        addedMedia = true;
                    }
                }
                break;
            case "video":
                if (arg.getSize() == 5) {
                    //full argumentList
                    VideoFile video = new VideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            (Integer) arg.get(4));
                    if (mR.insertMediaFile(video)) {
                        addedMedia = true;
                    }
                }
                if (arg.getSize() == 4) {
                    VideoFile video = new VideoFile(uploader, (Collection<Tag>) arg.get(1), (BigDecimal) arg.get(2), (Duration) arg.get(3),
                            0);
                    if (mR.insertMediaFile(video)) {
                        addedMedia = true;
                    }
                }
                break;
        }
        String s;
        if (addedMedia) {
            s = "Repository[" + mR.getNumberOfRepository() + "] added Media " + arg.getType();
        } else {
            s = "Repository[" + mR.getNumberOfRepository() + "] couldn't add Media " + arg.getType();
        }
        outputEvent = new OutputEvent(event, s);
        outputHandler.handle(outputEvent);
    }
}
