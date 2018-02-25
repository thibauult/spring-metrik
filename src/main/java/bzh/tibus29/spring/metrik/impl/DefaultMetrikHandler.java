package bzh.tibus29.spring.metrik.impl;

import bzh.tibus29.spring.metrik.MetrikContext;
import bzh.tibus29.spring.metrik.MetrikHandler;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultMetrikHandler implements MetrikHandler {

    /** The Logger */
    private final Logger log;

    private static final String STATUS_OK = "OK";
    private static final String STATUS_KO = "KO";

    public DefaultMetrikHandler(String loggerName) {
        this.log = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public void handleMetrik(MetrikContext context) {
        if(context.getMetrik().isEnabled()) {
            log.info(this.formatMetrik(context));
        }
    }

    protected String formatMetrik(MetrikContext context) {
        final String method = StringUtils.isEmpty(context.getMetrik().getMethod()) ?
                context.getMethodName() : context.getMetrik().getMethod();
        final String status = this.formatStatus(context.getException());
        final String params = this.formatParams(context.getMethodParams(), context.getMetrik().getParams());
        final String result = this.formatResult(context.getMethodResult(), context.getMetrik().getResultFields());

        return String.format("%s|%s|%s|%s|[%s]|[%s]", context.getMetrik().getValue(), method, context.getDuration(), status, params, result);
    }

    protected String formatStatus(Throwable exception) {
        return exception != null ? STATUS_KO : STATUS_OK;
    }

    protected String formatResult(Object result, List<String> resultFields) {
        if(resultFields.isEmpty()) {
            return result == null ? "" : String.valueOf(result);
        }
        else {
            return resultFields.stream()
                    .map(r -> r + "=" + getObjectProperty(result, r, "???"))
                    .collect(Collectors.joining(","));
        }
    }

    protected String formatParams(Map<String, Object> methodParams, List<String> metrikParams) {
        if(metrikParams.isEmpty()) {
            return methodParams.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining(","));
        }

        return metrikParams.stream().map(mp -> {

            final int dotIndex = mp.indexOf(".");
            if(dotIndex > 0) {
                String paramName = mp.substring(0, dotIndex);
                Object paramValue = methodParams.getOrDefault(paramName, null);
                final String subFieldName = mp.substring(dotIndex + 1, mp.length());
                return mp + "=" + getObjectProperty(paramValue, subFieldName, "???");
            }
            else {
                return mp + "=" + methodParams.getOrDefault(mp, "???");
            }
        }).collect(Collectors.joining(","));
    }

    protected String getObjectProperty(Object bean, String propertyName, String orElse) {
        try {
            return BeanUtils.getProperty(bean, propertyName);
        } catch (Exception e) {
            return orElse;
        }
    }
}
