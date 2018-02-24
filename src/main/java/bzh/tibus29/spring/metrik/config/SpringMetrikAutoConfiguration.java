package bzh.tibus29.spring.metrik.config;

import bzh.tibus29.spring.metrik.TimedMetrikHandler;
import bzh.tibus29.spring.metrik.aop.TimedAspect;
import bzh.tibus29.spring.metrik.impl.DefaultTimedMetrikHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.inject.Inject;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(SpringMetrikProperties.class)
public class SpringMetrikAutoConfiguration {

    @Inject
    private SpringMetrikProperties props;

    @Bean
    @ConditionalOnMissingBean
    public TimedMetrikHandler timedMetricHandler() {
        return new DefaultTimedMetrikHandler(this.props.getTimedLoggerName());
    }

    @Bean
    public TimedAspect springMetrikTimedAspect(TimedMetrikHandler handler) {
        return new TimedAspect(handler);
    }
}
