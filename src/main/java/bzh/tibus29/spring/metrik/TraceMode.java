package bzh.tibus29.spring.metrik;

public enum TraceMode {

    /**
     * See global configuration
     */
    GLOBAL,

    /**
     * Log all parameters and complete result
     */
    AUTO,

    /**
     * Define manually witch parameters and fields will be logged.
     * In that case, {@link Metrik#resultFields()} and {@link Metrik#params()} have to be defined.
     */
    MANUAL;

    /**
     * Trace all parameters
     */
    public static final String ALL = "*";
}
