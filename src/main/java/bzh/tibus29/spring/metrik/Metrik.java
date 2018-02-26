package bzh.tibus29.spring.metrik;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Metrik {

    enum TimeMode { NULL, MILLIS, NANO }

    /**
     * @return the metric group name. If not set, class name will be taken
     */
    String value() default "";

    /**
     * @return the metric name. If not set, method name will be taken
     */
    String method() default "";

    /**
     * @return the duration mode
     */
    TimeMode timeMode() default TimeMode.NULL;

    /**
     * @return metric enabled or not
     */
    boolean enabled() default true;

    /**
     * @return the logging mode
     */
    TraceMode traceMode() default TraceMode.GLOBAL;

    /**
     * @return params name to log. If not set, all params will be logged. Use '*' to log all parameters
     */
    String[] params() default "";

    /**
     * @return the result fields to log. If not set, all result object will be logged
     */
    String[] resultFields() default "";

    /**
     * @return force tracing event if {@link Metrik#traceMode()} is set to {@link TraceMode#MANUAL}
     */
    boolean traceResult() default false;
}
