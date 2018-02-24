package bzh.tibus29.spring.metrik.impl;

import bzh.tibus29.spring.metrik.ExecutionContext;
import bzh.tibus29.spring.metrik.TimedMetrikHandler;
import bzh.tibus29.spring.metrik.TimedWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

public class DefaultTimedMetrikHandler implements TimedMetrikHandler {

    /** The Logger */
    private final Logger log;

    private static final String STATUS_OK = "OK";
    private static final String STATUS_KO = "KO";

    public DefaultTimedMetrikHandler(String loggerName) {
        this.log = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public void handleMetric(ExecutionContext context) {

        final TimedWrapper annotation = context.getAnnotation();

        if(annotation.isEnabled()) {
            log.info("{}|{}|{}|{}|[{}]|[{}]",
                    annotation.getValue(),
                    StringUtils.isEmpty(annotation.getMethod()) ? context.getMethodName() : annotation.getMethod(),
                    context.getDuration(),
                    this.formatStatus(context.getException()),
                    this.formatParams(context.getParams()),
                    this.formatResult(context.getResult())
            );
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
