package bzh.tibus29.spring.metrik;

import java.util.Map;

public class MetrikContext {

    private long duration;
    private Throwable exception;
    private Map<String, Object> params;
    private Object result;
    private MetrikWrapper annotation;
    private String methodName;

    public MetrikContext() {
        // default constructor, nothing to do here
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setAnnotation(MetrikWrapper annotation) {
        this.annotation = annotation;
    }

    public MetrikWrapper getAnnotation() {
        return annotation;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

}
