package bzh.tibus29.spring.metrik.config;

import bzh.tibus29.spring.metrik.TraceMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("metrik")
public class SpringMetrikProperties {

    private String loggerName = "METRIK";
    private TraceMode globalTraceMode = TraceMode.MANUAL;

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public TraceMode getGlobalTraceMode() {
        return globalTraceMode;
    }

    public void setGlobalTraceMode(TraceMode globalTraceMode) {
        this.globalTraceMode = globalTraceMode;
    }
}
