package util;

public enum InputModeEnum {
    c("create:     "),
    d("delete:     "),
    r("read:       "),
    u("update:     "),
    p("persistence:"),
    s("storage:    ");

    private final String text;

    InputModeEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public char toChar() {
        return text.charAt(0);
    }
}
