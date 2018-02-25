package bzh.tibus29.spring.metrik;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Metrik {

    enum Mode { NULL, MILLIS, NANO }

    /**
     * Metric group name
     */
    String value() default "_";

    /**
     * Metric method name
     */
    String method() default "";

    /**
     * Duration mode
     */
    Mode mode() default Mode.NULL;

    /**
     * Metric enabled
     */
    boolean enabled() default true;
}
