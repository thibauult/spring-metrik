package bzh.tibus29.spring.metrik.handler;

import bzh.tibus29.spring.metrik.TraceMode;
import bzh.tibus29.spring.metrik.config.SpringMetrikProperties;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultMetrikHandler implements MetrikHandler {

    /** The Logger */
    private final Logger log;

    static final String STATUS_OK = "OK";
    static final String STATUS_KO = "KO";

    static final String UNKNOWN = "???";

    private final SpringMetrikProperties properties;

    @Inject
    public DefaultMetrikHandler(SpringMetrikProperties properties) {
        this.properties = properties;
        this.log = LoggerFactory.getLogger(properties.getLoggerName());
    }

    @Override
    public void handleMetrik(MetrikHandlerContext context) {
        if(context.getMetrik().isEnabled()) {
            log.info(this.formatMetrik(context));
        }
    }

    protected String formatMetrik(MetrikHandlerContext context) {

        TraceMode traceMode = context.getMetrik().getTraceMode();

        if(traceMode == TraceMode.GLOBAL) {
            traceMode = this.properties.getGlobalTraceMode();
        }

        final String method = StringUtils.isEmpty(context.getMetrik().getMethod()) ?
                context.getMethodName() : context.getMetrik().getMethod();
        final String status = this.formatStatus(context.getException());
        final String params = this.formatParams(
                traceMode,
                context.getMethodParams(),
                context.getMetrik().getParams()
        );
        final String result = this.formatResult(
                traceMode,
                context.getMethodResult(),
                context.getMetrik().getResultFields()
        );

        return String.format("%s|%s|%s|%s|[%s]|[%s]", context.getMetrik().getValue(), method, context.getDuration(), status, params, result);
    }

    protected String formatStatus(Throwable exception) {
        return exception != null ? STATUS_KO : STATUS_OK;
    }

    protected String formatResult(TraceMode traceMode, Object result, List<String> resultFields) {
        if(resultFields.isEmpty() && traceMode == TraceMode.AUTO) {
            return result == null ? "" : this.formatObject(result);
        }
        else if(!resultFields.isEmpty()) {
            return resultFields.stream()
                    .map(r -> r + "=" + getObjectProperty(result, r, UNKNOWN))
                    .collect(Collectors.joining(","));
        }
        else {
            return "";
        }
    }

    protected String formatParams(TraceMode traceMode, Map<String, Object> methodParams, List<String> metrikParams) {
        if(metrikParams.isEmpty() && traceMode == TraceMode.MANUAL) {
            return "";
        }
        else if((metrikParams.size() == 1 && metrikParams.get(0).equals(TraceMode.ALL)) || metrikParams.isEmpty()) {
            return methodParams.entrySet().stream()
                    .map(e -> e.getKey() + "=" + this.formatObject(e.getValue()))
                    .collect(Collectors.joining(","));
        }
        else {
            return metrikParams.stream().map(mp -> {

                final int dotIndex = mp.indexOf('.');
                if(dotIndex > 0) {
                    final String paramName = mp.substring(0, dotIndex);
                    final Object paramValue = methodParams.getOrDefault(paramName, null);
                    final String subFieldName = mp.substring(dotIndex + 1, mp.length());
                    return mp + "=" + getObjectProperty(paramValue, subFieldName, UNKNOWN);
                }
                else {
                    return mp + "=" + this.formatObject(methodParams.getOrDefault(mp, UNKNOWN));
                }
            }).collect(Collectors.joining(","));
        }
    }

    protected String getObjectProperty(Object bean, String propertyName, String orElse) {
        try {
            return this.formatObject(PropertyUtils.getProperty(bean, propertyName));
        } catch (Exception e) {
            log.warn("Unable to get property \"{}\" from bean {}, cause : {}", propertyName, bean, e.getMessage());
            return orElse;
        }
    }

    protected String formatObject(Object bean) {
        if (bean instanceof String && !bean.equals(UNKNOWN)) {
            return "'" + bean + "'";
        }
        else {
            return String.valueOf(bean);
        }
    }
}
