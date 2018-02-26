package bzh.tibus29.spring.metrik.handler;

import bzh.tibus29.spring.metrik.TraceMode;
import bzh.tibus29.spring.metrik.Metrik;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetrikWrapper {

    private String value;
    private String method;
    private boolean enabled;
    private List<String> params;
    private List<String> resultFields;

    private Metrik.TimeMode timeMode;
    private TraceMode traceMode;

    public MetrikWrapper() {
        // default constructor, nothing to do here
    }

    public MetrikWrapper(Metrik source) {
        this();
        this.value = source.value();
        this.method = source.method();
        this.timeMode = source.timeMode() == Metrik.TimeMode.NULL ? Metrik.TimeMode.MILLIS : source.timeMode();
        this.enabled = source.enabled();
        this.params = arrayToList(source.params());
        this.resultFields = arrayToList(source.resultFields());
        this.traceMode = source.traceMode();
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

    public Metrik.TimeMode getTimeMode() {
        return timeMode;
    }

    public void setTimeMode(Metrik.TimeMode timeMode) {
        this.timeMode = timeMode;
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

    public TraceMode getTraceMode() {
        return traceMode;
    }

    public void setTraceMode(TraceMode traceMode) {
        this.traceMode = traceMode;
    }
}
