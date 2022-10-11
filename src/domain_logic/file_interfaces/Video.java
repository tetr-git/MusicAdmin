package domain_logic.file_interfaces;

public interface Video extends MediaContent, Uploadable {
    int getResolution();
}
