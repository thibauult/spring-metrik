package bzh.tibus29.spring.metrik.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("metrik")
public class SpringMetrikProperties {

    private String loggerName = "METRIK";

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }
}
