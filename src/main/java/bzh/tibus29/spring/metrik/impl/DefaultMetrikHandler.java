package bzh.tibus29.spring.metrik.impl;

import bzh.tibus29.spring.metrik.MetrikContext;
import bzh.tibus29.spring.metrik.MetrikHandler;
import bzh.tibus29.spring.metrik.MetrikWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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

        final MetrikWrapper annotation = context.getAnnotation();

        if(annotation.isEnabled()) {

            final String method = StringUtils.isEmpty(annotation.getMethod()) ?
                    context.getMethodName() : annotation.getMethod();
            final String status = this.formatStatus(context.getException());
            final String params = this.formatParams(context.getParams());
            final String result = this.formatResult(context.getResult());

            log.info("{}|{}|{}|{}|[{}]|[{}]", annotation.getValue(), method, context.getDuration(), status, params, result);
        }
    }

    protected String formatStatus(Throwable exception) {
        return exception != null ? STATUS_KO : STATUS_OK;
    }

    protected String formatResult(Object result) {
        return result == null ? "" : result.toString();
    }

    protected String formatParams(Map<String, Object> params) {
        return params.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(","));
    }
}
