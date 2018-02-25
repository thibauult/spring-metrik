package bzh.tibus29.spring.metrik;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetrikWrapper {

    private String value;
    private String method;
    private Metrik.Mode mode;
    private boolean enabled;
    private List<String> params;
    private List<String> resultFields;

    public MetrikWrapper() {
        // default constructor, nothing to do here
    }

    public MetrikWrapper(Metrik source) {
        this();
        this.value = source.value();
        this.method = source.method();
        this.mode = source.mode() == Metrik.Mode.NULL ? Metrik.Mode.MILLIS : source.mode();
        this.enabled = source.enabled();
        this.params = arrayToList(source.params());
        this.resultFields = arrayToList(source.resultFields());
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
        return StringUtils.isEmpty(this.value);
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(String... params) {
        this.params = arrayToList(params);
    }

    public List<String> getResultFields() {
        return resultFields;
    }

    public void setResultFields(String... resultFields) {
        this.resultFields = arrayToList(resultFields);
    }

    private static List<String> arrayToList(String... params) {
        return Stream.of(params).filter(p -> !p.isEmpty()).collect(Collectors.toList());
    }
}
