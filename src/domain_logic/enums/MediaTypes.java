package domain_logic.enums;

public enum MediaTypes {
    ALL_TYPES("Audio, AudioVideo, InteractiveVideo, LicensedAudio, LicensedAudioVideo, LicensedVideo"),
    AUDIO("Audio"),
    ViDEO("Video"),
    AUDIOVIDEO("AudioVideo"),
    INTERACTIVEVIDEO("InteractiveVideo"),
    LICENSEDAUDIO("LicensedAudio"),
    LICENSEDAUDIOVIDEO("LicensedAudioVideo"),
    LICENSEDVIDEO("LicensedVideo");

    private final String text;

    MediaTypes(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}