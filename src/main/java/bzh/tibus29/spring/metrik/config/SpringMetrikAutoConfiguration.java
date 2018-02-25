package bzh.tibus29.spring.metrik.config;

import bzh.tibus29.spring.metrik.MetrikHandler;
import bzh.tibus29.spring.metrik.aop.MetrikAspect;
import bzh.tibus29.spring.metrik.impl.DefaultMetrikHandler;
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
    public MetrikHandler timedMetricHandler() {
        return new DefaultMetrikHandler(this.props.getTimedLoggerName());
    }

    @Bean
    public MetrikAspect springMetrikTimedAspect(MetrikHandler handler) {
        return new MetrikAspect(handler);
    }
}
