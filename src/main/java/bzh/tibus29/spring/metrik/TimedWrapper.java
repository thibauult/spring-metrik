package bzh.tibus29.spring.metrik;

public class TimedWrapper {

    private String value;
    private String method;
    private Timed.Mode mode;
    private boolean enabled;

    public TimedWrapper() {
    }

    public TimedWrapper(Timed source) {
        this();
        this.value = source.value();
        this.method = source.method();
        this.mode = source.mode() == Timed.Mode.NULL ? Timed.Mode.MILLIS : source.mode();
        this.enabled = source.enabled();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Timed.Mode getMode() {
        return mode;
    }

    public void setMode(Timed.Mode mode) {
        this.mode = mode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDefaultValue() {
        return this.value.equals("_");
    }
}
