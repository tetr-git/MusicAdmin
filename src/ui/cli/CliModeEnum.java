package ui.cli;

public enum CliModeEnum {
    c("create:     "),
    d("delete:     "),
    r("read:       "),
    u("update:     "),
    p("persistence:"),
    s("storage:    ");

    private final String text;

    CliModeEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
