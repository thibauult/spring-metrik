package bzh.tibus29.spring.metrik;

public class MetrikWrapper {

    private String value;
    private String method;
    private Metrik.Mode mode;
    private boolean enabled;

    public MetrikWrapper() {
    }

    public MetrikWrapper(Metrik source) {
        this();
        this.value = source.value();
        this.method = source.method();
        this.mode = source.mode() == Metrik.Mode.NULL ? Metrik.Mode.MILLIS : source.mode();
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

    public Metrik.Mode getMode() {
        return mode;
    }

    public void setMode(Metrik.Mode mode) {
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
