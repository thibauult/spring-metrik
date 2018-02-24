package bzh.tibus29.spring.metrik.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("metrik")
public class SpringMetrikProperties {

    private String timedLoggerName = "METRIK";

    public String getTimedLoggerName() {
        return timedLoggerName;
    }

    public void setTimedLoggerName(String timedLoggerName) {
        this.timedLoggerName = timedLoggerName;
    }
}
