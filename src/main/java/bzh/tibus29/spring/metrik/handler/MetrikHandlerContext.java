package bzh.tibus29.spring.metrik.handler;

import java.util.Map;

public class MetrikHandlerContext {

    private MetrikWrapper metrik;
    private long duration;
    private Throwable exception;
    private Map<String, Object> methodParams;
    private Object methodResult;
    private String methodName;

    public MetrikHandlerContext() {
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

    public void setMethodParams(Map<String, Object> params) {
        this.methodParams = params;
    }

    public Map<String, Object> getMethodParams() {
        return methodParams;
    }

    public void setMethodResult(Object result) {
        this.methodResult = result;
    }

    public Object getMethodResult() {
        return methodResult;
    }

    public void setMetrik(MetrikWrapper metrik) {
        this.metrik = metrik;
    }

    public MetrikWrapper getMetrik() {
        return metrik;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

}
